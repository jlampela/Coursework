//TEKIJÄT:
//JOHANNES LAMPELA 2689160
//TOMMY MERILÄINEN 2685805
//ATTE MARJAKANGAS 2591410

/* C Standard library */
#include <stdio.h>
#include <string.h>

/* XDCtools files */
#include <xdc/std.h>
#include <xdc/runtime/System.h>

/* BIOS Header files */
#include <ti/sysbios/BIOS.h>
#include <ti/sysbios/knl/Clock.h>
#include <ti/sysbios/knl/Task.h>
#include <ti/drivers/PIN.h>
#include <ti/drivers/i2c/I2CCC26XX.h>
#include <ti/drivers/pin/PINCC26XX.h>
#include <ti/drivers/I2C.h>
#include <ti/drivers/Power.h>
#include <ti/drivers/power/PowerCC26XX.h>
#include <ti/drivers/UART.h>

/* Board Header files */
#include "buzzer.h"
#include "Board.h"
#include "wireless/comm_lib.h"
#include "sensors/mpu9250.h"
    
/* Task */
#define STACKSIZE 2048
Char sensorTaskStack[STACKSIZE];
Char commTaskStack[STACKSIZE];
Char musicTaskStack[STACKSIZE];
Char beepTaskStack[STACKSIZE];

// Tilakoneen esittely
enum state { MENU=0, READ, DOWN, UP, LEFT, RIGHT, RIP };
enum state programState = MENU;

// MPU power pin global variables
static PIN_Handle hMpuPin;
static PIN_State  MpuPinState;

// MPU power pin
static PIN_Config MpuPinConfig[] = {
    Board_MPU_POWER  | PIN_GPIO_OUTPUT_EN | PIN_GPIO_HIGH | PIN_PUSHPULL | PIN_DRVSTR_MAX,
    PIN_TERMINATE
};

// MPU uses its own I2C interface
static const I2CCC26XX_I2CPinCfg i2cMPUCfg = {
    .pinSDA = Board_I2C0_SDA1,
    .pinSCL = Board_I2C0_SCL1
};

// BUZZER GLOBAL VARIABLES
static PIN_Handle hBuzzer;
static PIN_State sBuzzer;
PIN_Config cBuzzer[] = {
  Board_BUZZER | PIN_GPIO_OUTPUT_EN | PIN_GPIO_LOW | PIN_PUSHPULL | PIN_DRVSTR_MAX,
  PIN_TERMINATE
};

// RTOS:n globaalit muuttujat pinnien käyttöön
static PIN_Handle buttonHandle;
static PIN_State buttonState;

PIN_Config buttonConfig[] = {
   Board_BUTTON0  | PIN_INPUT_EN | PIN_PULLUP | PIN_IRQ_NEGEDGE, 
   PIN_TERMINATE // Asetustaulukko lopetetaan aina tällä vakiolla
};

/* Task Functions */
// liikeanturitaski
Void sensorFxn(UArg arg0, UArg arg1) {
    
    float ax, ay, az, gx, gy, gz;
    char msg[30];

    I2C_Handle i2cMPU; // Own i2c-interface for MPU9250 sensor
    I2C_Params i2cMPUParams;
    I2C_Params_init(&i2cMPUParams);
    i2cMPUParams.bitRate = I2C_400kHz;
	// Note the different configuration below
    i2cMPUParams.custom = (uintptr_t)&i2cMPUCfg;

    // MPU power on
    PIN_setOutputValue(hMpuPin,Board_MPU_POWER, Board_MPU_POWER_ON);
    
    // Wait 100ms for the MPU sensor to power up
	Task_sleep(100000 / Clock_tickPeriod);
    System_printf("MPU9250: Power ON\n");
    System_flush();
   
    // MPU open i2c
    i2cMPU = I2C_open(Board_I2C, &i2cMPUParams);
    if (i2cMPU == NULL) {
        System_abort("Error Initializing I2CMPU\n");
    }
    
    // MPU setup and calibration
    	System_printf("MPU9250: Setup and calibration...\n");
    	System_flush();
    	mpu9250_setup(&i2cMPU);
    	System_printf("MPU9250: Setup and calibration OK\n");
    	System_flush();
    	
    I2C_close(i2cMPU);

    while (1) {
        
        if(programState == READ){
            
            // MPU open i2c
            i2cMPU = I2C_open(Board_I2C, &i2cMPUParams);
            if (i2cMPU == NULL) {
                System_abort("Error Initializing I2CMPU\n");
            }
            
            mpu9250_get_data(&i2cMPU, &ax, &ay, &az, &gx, &gy, &gz);
            memset(msg,0,16);
            I2C_close(i2cMPU);

            if(gx > 65){
                programState = UP;
                beepFxn();
                //System_printf("up\n");
                //System_flush();
                sprintf(msg, "id:58,EAT:1,MSG1:nam nam");
                Send6LoWPAN(IEEE80154_SERVER_ADDR, msg, strlen(msg));
                StartReceive6LoWPAN();
                Task_sleep(2500000 / Clock_tickPeriod);
            }
            else if(gx < -65){
                programState = DOWN;
                beepFxn();
                //System_printf("down\n");
                //System_flush();
                sprintf(msg, "id:58,ACTIVATE:2;2;2");
                Send6LoWPAN(IEEE80154_SERVER_ADDR, msg, strlen(msg));
                StartReceive6LoWPAN();
                Task_sleep(2500000 / Clock_tickPeriod);
            }
            else if(gy > 65){
                programState = RIGHT;
                beepFxn();
                //System_printf("right\n");
                //System_flush();
                sprintf(msg, "id:58,PET:1,MSG1:Feels good");
                Send6LoWPAN(IEEE80154_SERVER_ADDR, msg, strlen(msg));
                StartReceive6LoWPAN();
                Task_sleep(2500000 / Clock_tickPeriod);
            }
            else if(gy < -65){
                programState = LEFT;
                beepFxn();
                //System_printf("left\n");
                //System_flush();
                sprintf(msg, "id:58,EXERCISE:1,MSG1:hmmm");
                Send6LoWPAN(IEEE80154_SERVER_ADDR, msg, strlen(msg));
                StartReceive6LoWPAN();
                Task_sleep(2500000 / Clock_tickPeriod);
            }
            programState = READ;
        Task_sleep(250000 / Clock_tickPeriod);            
        }
    }
	    // Program never gets here..
    // MPU close i2c
    // I2C_close(i2cMPU);
	    // MPU power off
    // PIN_setOutputValue(hMpuPin,Board_MPU_POWER, Board_MPU_POWER_OFF);
}

// Tiedonsiirtotaski
Void commTaskFxn(UArg arg0, UArg arg1) {

   char payload[80]; // viestipuskuri
   uint16_t senderAddr;

   // Radio alustetaan vastaanottotilaan
   int32_t result = StartReceive6LoWPAN();
   if(result != true) {
      System_abort("Wireless receive start failed");
   }
   
   const char delim[2] = ","; //char *strtok(char *str, const char *delim)
   char *id;
   char *status;
   int x;
   
   // Vastaanotetaan viestejä loopissa
   while (true) {

        // HUOM! VIESTEJÄ EI SAA LÄHETTÄÄ TÄSSÄ SILMUKASSA
        // Viestejä lähtee niin usein, että se tukkii laitteen radion ja 
        // kanavan kaikilta muilta samassa harjoituksissa olevilta!!

        // jos true, viesti odottaa
        if (GetRXFlag()) {

           // Tyhjennetään puskuri (ettei sinne jäänyt edellisen viestin jämiä)
           memset(payload,0,80);
           // Luetaan viesti puskuriin payload
           Receive6LoWPAN(&senderAddr, payload, 80);
           // Luetaan muokattua merkkijonoa ja pilkotaan osiksi
           // Otetaan ensimmäinen merkki talteen (LAITTEEN ID) ilman pilkkua
           id = strtok(payload, delim);
           // Sitten loppuosa talteen
           status = strtok(NULL, delim);
           //System_printf(payload);
           //System_printf(id);
           //System_printf(status);
           // Jos lähetetyn viestin id on meidän laitteen -> tapahtuu asioita..
           // int strcmp (const char* str1, const char* str2)
           if(strcmp(id, "58") == 0){
               x = strlen(status);
               if (x == 13){ //Jos id jälkeinen viesti on BEEP:Too late -> tama karkasi
                   System_printf("Lemmikki karkasi!\n");
                   programState = RIP;
               }
               else{
                   System_printf("%s\n", status);
                   System_flush();
                   beepFxn();
                }
            }
        }
    }
}

// TASK FOR IN-GAME MUSIC
Void musicFxn(UArg arg0, UArg arg1) {
    while (1) {
        if (programState == RIP){
			buzzerOpen(hBuzzer);
			buzzerSetFrequency(520);
			Task_sleep(80000/Clock_tickPeriod);
			buzzerSetFrequency(590);
			Task_sleep(80000/Clock_tickPeriod);
			buzzerSetFrequency(520);
			Task_sleep(80000/Clock_tickPeriod);
			buzzerSetFrequency(590);
			Task_sleep(80000/Clock_tickPeriod);
			buzzerSetFrequency(640);
			Task_sleep(150000/Clock_tickPeriod);
			buzzerClose();
			Task_sleep(300000/Clock_tickPeriod);
		}
		
		else if (programState == READ){
			buzzerOpen(hBuzzer);
			buzzerSetFrequency(262); // c
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerSetFrequency(262); // c
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerSetFrequency(262); // c
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerSetFrequency(330); // e
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerClose();
			Task_sleep(75000/Clock_tickPeriod);
			
			buzzerOpen(hBuzzer);
			buzzerSetFrequency(293); // d
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerSetFrequency(293); // d
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerSetFrequency(293); // d
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerSetFrequency(350); // f
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerClose();
			Task_sleep(75000/Clock_tickPeriod);
			
			buzzerOpen(hBuzzer);
			buzzerSetFrequency(330); // e
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerSetFrequency(330); // e
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerSetFrequency(293); // d
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerSetFrequency(293); // d
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerSetFrequency(262); // c
			Task_sleep(200000 / Clock_tickPeriod);
			buzzerClose();
			Task_sleep(75000/Clock_tickPeriod);
		}   

	Task_sleep(900000 / Clock_tickPeriod);
	}
}

// BEEP SOUND
void beepFxn(){
    Task_sleep(500000 / Clock_tickPeriod);
    buzzerOpen(hBuzzer);
    buzzerSetFrequency(450);
    Task_sleep(500000 / Clock_tickPeriod);
    buzzerClose();
}

// TASK TO START
Void buttonFxn(PIN_Handle handle, PIN_Id pinId) {
	if (programState == MENU) {
		programState = READ;
	}
	else{
		programState = MENU;
	}
}

Int main(void) {
    
    // Task variables
    
    Task_Handle sensorTask;
    Task_Params sensorTaskParams;
    
    Task_Handle commTask;
    Task_Params commTaskParams;
    
    Task_Handle musicTask;
    Task_Params musicTaskParams;
    
    Task_Handle beepTask;
    Task_Params beepTaskParams;
    
    
    // Initialize board
    Board_initGeneral();
    Board_initUART();
    Board_initI2C();
    Init6LoWPAN();
    
    // Buzzer
    hBuzzer = PIN_open(&sBuzzer, cBuzzer);
    if (hBuzzer == NULL) {
        System_abort("Pin open failed!");
    }
    
    // Open MPU power pin
    hMpuPin = PIN_open(&MpuPinState, MpuPinConfig);
    if (hMpuPin == NULL) {
    	System_abort("Pin open failed!");
    }
    
    // Otetaan pinnit käyttöön ohjelmassa
    buttonHandle = PIN_open(&buttonState, buttonConfig);
    if(!buttonHandle) {
        System_abort("Error initializing button pins\n");
    }
    // Asetetaan painonappi-pinnille keskeytyksen käsittelijäksi
    // funktio buttonFxn
    if (PIN_registerIntCb(buttonHandle, &buttonFxn) != 0) {
        System_abort("Error registering button callback function");
    }
    
    /* Tasks */
    
    //Sensor task
    Task_Params_init(&sensorTaskParams);
    sensorTaskParams.stackSize = STACKSIZE/2;
    sensorTaskParams.stack = &sensorTaskStack;
    sensorTaskParams.priority=2;
    sensorTask = Task_create((Task_FuncPtr)sensorFxn, &sensorTaskParams, NULL);
    if (sensorTask == NULL) {
    	System_abort("Task create failed!");
    }
    
    //Communication task
    Task_Params_init(&commTaskParams);
    commTaskParams.stackSize = STACKSIZE;
    commTaskParams.stack = &commTaskStack;
    commTaskParams.priority = 1;
    commTask = Task_create((Task_FuncPtr)commTaskFxn, &commTaskParams, NULL);
    if (commTask == NULL) {
    	System_abort("Task create failed!");
    }
    
    /* SOUNDS */
    
    //IN GAME MUSIC
    Task_Params_init(&musicTaskParams);
    musicTaskParams.stackSize = STACKSIZE;
    musicTaskParams.stack = &musicTaskStack;
    musicTaskParams.priority=2;
    musicTask = Task_create((Task_FuncPtr)musicFxn, &musicTaskParams, NULL);
    if (musicTask == NULL) {
        System_abort("Task create failed!");
    }
    
    //BEEP SOUND
    Task_Params_init(&beepTaskParams);
    beepTaskParams.stackSize = STACKSIZE/2;
    beepTaskParams.stack = &beepTaskStack;
    beepTaskParams.priority=2;
    beepTask = Task_create((Task_FuncPtr)beepFxn, &beepTaskParams, NULL);
    if (beepTask == NULL) {
        System_abort("Task create failed!");
    }

    /* Start BIOS */
    BIOS_start();

    return (0);
}
