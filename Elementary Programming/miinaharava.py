import haravasto as ha
import random
import time
from datetime import datetime

tila = {
    "kentta": [],
    "pohjakentta": [],
    "miinat": 0,
    "liput": [],
    "kelloalku": None,   
    "kellokiinni": None,   
    "miinanpaikat": [],
    "klik": 0,
    "tulos": False
}

def asetukset():
    """
    Kysyy kentälle mitat sekä miinojen määrän
    """
    while True:
        try:
            leveys = int(input("Anna kentän leveys: "))
            korkeus = int(input("Anna kentän korkeus: "))
            miinat = int(input("Anna miinojen määrä: "))
            if leveys < 1 or korkeus < 1:
                print("Liian pieni kenttä!")
                return asetukset()
            elif miinat > leveys*korkeus or miinat < 1:
                print("Liikaa tai liian vähän miinoja!")
                return asetukset()
            else:
                return leveys, korkeus, miinat
        except ValueError:
            print("Virheellinen luku!")

def luo_kentta():
    """
    Luo kentän
    """
    leveys, korkeus, miinat = asetukset()

    kentta = []
    for _ in range(korkeus):
        kentta.append([])
        for _ in range(leveys):
            kentta[-1].append(" ")

    pohjakentta = []
    for _ in range(korkeus):
        pohjakentta.append([])
        for _ in range(leveys):
            pohjakentta[-1].append(" ")

    tila["kentta"] = kentta
    tila["pohjakentta"] = pohjakentta
    miinoita(pohjakentta, miinat)
    numeroi()
    kellopaalle()

def numeroi():
    """
    Numeroi ruudut
    """
    for y in range(len(tila["kentta"])):
        for x in range(len(tila["kentta"][0])):
            if tila["pohjakentta"][y][x] != "x":
                tila["pohjakentta"][y][x] = str(naapurimiinat(x,y))

def naapurimiinat(x, y):
    """
    Etsii naapurimiinat
    """
    naapurimiinat = 0

    for i in range(y-1, y+2):
        for j in range(x-1, x+2):
            if 0 <= i < len(tila["kentta"]) and 0 <= j < len(tila["kentta"][0]):
                if tila["pohjakentta"][i][j] == "x":
                    naapurimiinat += 1
    return naapurimiinat

def naapuri(x, y):
    """
    laskee kentän koon ja tämän jälkeen hakee koordinaatteja
    """
    naapurit = []

    for i in range(-1, 2):
        for j in range(-1, 2):
            if -1 < (x + i) < len(tila["kentta"]) and -1 < (y + j) < len(tila["kentta"][0]):
                naapurit.append((x + i, y + j))
    return naapurit

def miinoita(pohjakentta, miina):
    """
    Asettaa kentälle N kpl miinoja satunnaisiin paikkoihin.
    """
    miinat = []
    miinacounter = 0
    while miinacounter < miina:
        x = random.randint(0, len(pohjakentta[0])-1)
        y = random.randint(0, len(pohjakentta)-1)
        if pohjakentta[y][x] == " ":
            pohjakentta[y][x] = "x"
            miinacounter = miinacounter + 1
            miinat.append((x, y))

    tila["miinat"] = miinacounter
    tila["pohjakentta"] = pohjakentta
    tila["miinanpaikat"] = miinat
    
def ruudunavaus(x, y):
    """
    Ruudun avaus
    """
    if tila["kentta"][y][x] != " ":
        print("Ruutu on valittu jo")
    elif tila["pohjakentta"][y][x] == "x":
        havio(x, y)
    elif tila["pohjakentta"][y][x] == "0":
        tulvataytto(x, y)
    else:
        tila["kentta"][y][x] = tila["pohjakentta"][y][x]
  
def havio(x, y):
    """
    miinaa häviää
    """
    tila["kentta"][y][x] = tila["pohjakentta"][y][x]
    print("Ruudussa oli miina! Hävisit pelin!")
    tila["kentta"] = tila["pohjakentta"]
    tila["tulos"] = "Hävisit pelin"
    kellokiinni()
    tallenna_tulokset()

def voitto(x, y):
    """
    kaikki muut paitsi miina ruudut avattu niin voitat
    """
    a = 0
    for rivi in tila["kentta"]:
        for alkio in rivi:
            if alkio == " " or alkio == "f":
                a += 1       
    if a == tila["miinat"]:
        print("Voitit pelin!!")
        tila["kentta"] = tila["pohjakentta"]
        kellokiinni()
        tila["tulos"] = "Voitit pelin"
        tallenna_tulokset()
        
def liputus(x, y):
    """
    asettaa lipun ruutuun tai poistaa sen
    """
    if tila["kentta"][y][x] == " ":
        tila["kentta"][y][x] = "f"
    elif tila["kentta"][y][x] == "f":
        tila["kentta"][y][x] = " "

def tulvataytto(x, y):
    """
    Merkitsee kentällä olevat tuntemattomat alueet turvalliseksi siten, että
    täyttö aloitetaan annetusta x, y -pisteestä.
    """
    lista = [(y, x)]
    while len(lista) > 0:
        x, y = lista.pop()
        if tila["pohjakentta"][x][y] == "0":
            for i, k in naapuri(x, y):
                if tila["kentta"][i][k] == " ":
                    tila["kentta"][i][k] = tila["pohjakentta"][i][k]
                    lista.append((i, k))

def hiiri_kasittelija(x, y, painike, muokkaus):
    """
    Tätä funktiota kutsutaan kun käyttäjä klikkaa sovellusikkunaa hiirellä.
    """
    x = int(x / 40)
    y = int(y / 40)
    if painike == ha.HIIRI_VASEN:
        tila["klik"] += 1
        ruudunavaus(x, y)
        voitto(x, y)
    elif painike == ha.HIIRI_OIKEA:
        tila["klik"] += 1
        liputus(x, y)

def piirra_kentta():
    """
    Käsittelijäfunktio, joka piirtää kaksiulotteisena listana kuvatun miinakentän
    ruudut näkyviin peli-ikkunaan. Funktiota kutsutaan aina kun pelimoottori pyytää
    ruudun näkymän päivitystä.
    """
    ha.tyhjaa_ikkuna()
    ha.piirra_tausta()
    ha.aloita_ruutujen_piirto()
    for y, k in enumerate(tila["kentta"]):
        for x, i in enumerate(k):
            ha.lisaa_piirrettava_ruutu(i, x * 40, y * 40)
            ha.piirra_ruudut()

def tulokset():
    """
    Avaa tulokset
    """
    try:
        with open("tulokset.txt", "r") as lahde:
            print(lahde.read())
    except IOError:
        print("Tiedoston aukaiseminen epäonnistui.")

def tallenna_tulokset():
    """
    Tallentaa tulokset
    """
    pelinkesto = tila["kellokiinni"]
    paiva = datetime.now()
    paivamaara = paiva.strftime("%d/%m/%Y %H:%M")
    vuorot = tila["klik"]
    lopputulos = tila["tulos"]
    a = len(tila["kentta"][0])
    b = len(tila["kentta"])
    miinoja = tila["miinat"]
    try:
        with open("tulokset.txt", "a+") as filu:
            filu.write("Päivämäärä: {}, Pelin kesto: {} sekunttia, Klikkauksia: {}, Lopputulos: {}, Kentän koko: {}x{} ja Miinojen lukumäärä: {} \n".format(paivamaara, round(pelinkesto, 2), vuorot, lopputulos, a, b, miinoja))
    except IOError:
        print("Kohdetiedostoa ei voitu avata. Tallennus epäonnistui")
    
def tyhjenna():
    """
    Tyhjentaa tilastot
    """
    try:
        with open("tulokset.txt", "r+") as tyhja:
            tyhja.truncate(0)
    except IOError:
        print("Tilastojen tyhjennys epäonnistui.")

def kellopaalle():
    """
    lyö kellon päälle
    """
    kello1 = time.time()
    tila["kelloalku"] = kello1

def kellokiinni():
    """
    Sammuttaa kellon ja laskee pelinkeston.
    """
    kello2 = time.time()
    pelinkesto = kello2 - tila["kelloalku"]
    tila["kellokiinni"] = pelinkesto

def mita_tehda():
    """
    Valikko
    """
    try:
        print("Paina 1, jos haluat pelata uuden pelin, paina 2, jos haluat sammuttaa pelin, paina 3, jos haluat katsella tilastoja tai paina 4, jos haluat tyhjentää tilastot!")
        valinta = int(input("Syötä valintasi: "))
        if valinta == 1:
            luo_kentta()
            ha.lataa_kuvat("spritet")
            ha.luo_ikkuna(len(tila["kentta"][0]) * 40, len(tila["kentta"]) * 40)
            ha.aseta_piirto_kasittelija(piirra_kentta)
            ha.aseta_hiiri_kasittelija(hiiri_kasittelija)
            kellopaalle()
            ha.aloita()
        elif valinta == 2:
            quit()
        elif valinta == 3:
            try:
                tulokset()
            except IOError:
                print("Virhe")
        elif valinta == 4:
            tyhjenna()
    except ValueError:
        print("Virheellinen luku")

def main():
    while True:
        mita_tehda()
    
if __name__ == "__main__":
    main()