# My project report

## EDIT:
I reworked the whole project making it way better and a lot more faster. I made a generic quicksort in its own class, in bookimplementation I made multiple fixes to make it
faster, BST class I made it in a way that its only a BST, made Node class with inserting method and created WordCount class which only stores the string name and count so 
I don't carry out useless information to the top 100 list afterwards. To create the list with words I created inOrder method in BST class which only stores maximum of 
100 words which means that I don't need to add the hundreds of thousands of words to the list and then sort it etc. Now the inOrder method adds words until its full (100),
then it checks if there is a word with smaller count if so it swaps it to the list. This way I have the top 100 words list and can easily and quickly sort by using the
generic quicksort algorithm. Also I only used a simple string array for the words that were going to be ignored since there really weren't many words needed to be ignored so I thought it wouldn't really matter if I used array or bst for them.

This new version of the project is way faster since the first version on my PC was about 3,5-4s in average and now its around 0.85s. That means that it's 4-5x times faster than the original version. One thing I found that if there is a lot of words but not so many unique words it slows the program down but if there is a lot unique words
it's way faster.

All in all i'm pretty happy with outcome of this new version since it's way faster and a lot more object-oriented. 














## My implementation choises
I chose to use binary search trees as the data structure since I used hash tables in the phonebook exercise. I also used recursive quicksort 
to sort the words by count. I also tried bubblesort (o(n^2) waaay toooo slooooow) and tried to make non recursive mergesort but for some
reason it didn't get it to work.. I made both files that you need to ignore and read into to 2 own trees. My implementation
gets the top-100 list by first traversing inorder the tree where the words are and adding the nodes into array and then use quicksort
to sort it by count. Quicksort (best case and average case O(n*log(n)), worst case O(n^2)).

## Correctness
My implementation works fine even though I'm not so happy with the result since it came out slower than I wanted and not so
"smoooth" as I wanted.
Probably making some non recursive sorting method to work and making the top100 list that way that there is always max 100 nodes would 
have made it a lot faster. (wouldn't have needed to increase the default stack size..). Thats probably why the execution time increases so much when there is a lot of unique words. I think that hash tables would have come in handy in this project..

## Time complexity
I think the time complexity should be something like O(n*log(n)) and looking at the graph you can see that times where the is
a lot of unique words the execution time spikes a lot even though there is less words. I think it's the sorting
algorithm (Worst case(O(n^2))) is the reason for that. Another may be the addNode function I think that it also could be 
for those spikes since when there is a lot of unique words it could become O(2^N).
All in all for my solution it reads Bulk.txt + ignore-words.txt in on average 3,5s on my pc (5600x + 16gb ram) so not so bad.
With further optimisation for those things I mentioned I think that i could get it a lot more faster..

## Final words
I think the most difficult part was to make it work. For some unknown reason it was really difficult to get a good idea what to
(1st time in the whole course) but making it piece by piece I got it to work. Biggest learning from this project is probably
how much it slows down if you use slow algorithms and such.