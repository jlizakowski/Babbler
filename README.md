# Babbler
An AI tool that finds patterns in streams of data, and generates data of the same form.

From the original Readme:
"The babbler is like the part of the mind of a natural speaker which can write sentences without 
thinking about grammer.  It is free thought, rambling, babbling, in need of some direction.  
It is the parts of the mind that can proofread a document and detect when something sounds wrong."

## Caveats
* The code has sat unused for about a decade (approx 2004 through 2006).  It was a work in progress.  I wouldn't write it the same way today.
* I'm releasing it as open-source, if anyone is interested in extending it or experimenting with the concepts.
* I learned about Markov chains while building this, so the implementation might be non-standard.
* The part of the code that is of most interest is the data structure, and how it can be used.
* The original version was written in the 1990's, in Excel spreadsheet format.  Later versions were in Perl and PHP4.  There were several Java versions, this being the most recent.

## Applications
* Text Generation (Babbling)
* Text identification
* Changing existing text to match a certain writing style (style impersonation)
* Data compression

Most of these applications are inherent to any tool that represents data in this fashion.  These were not intended features, but result from the nature of the tool. This type of tool is also used for speech recognition and text auto-complete.  

This code, in its current form, only learns then babbles.  Other versions of the code, such as the compression tests, might be integrated in the future.

## Quick example output

### Single source of text
##### Input:  
	King James Bible (Project Gutenberg)
##### Output:  
```
2:19 And Peter said, Blessed is that great glory of the son of Jezreel of his charge of the body; is Lord GOD;
Behold, with me that pass in thy sight.

1:10 Then did the angel that thou hast enlarged my strength faileth.
```

### Multiple sources of text
##### Input:  
	First 200 MB of project Gutenberg (entire corpus)
##### Output:  
```
The red light in the sky, and the stout gentleman present who had accompanied the prince and princess were settled by the Monthly Magazine, and all that she had said to him at the moment that he was at the back of the house, and on the following day he went to the king and the queen was a witch, and her eyes on him so that he could not see his face, as he took his seat in the House of Commons, and a mouth that was always in the same place, and the same areas of land and water, and be unclean until the even.
```

### Audio
##### Input: 
	A song by Deadmau5.
##### Ouput: 
	A repeated/looped excerpt of the Song.
<a href="com/lizakowski/tools/AI/output/Remote_Deadmau5.8khz.8bitsigned.withwhitenoise.x18.raw.wav">Link to WAV</a>.

### Words invented
Many of these derive from Babbling AP News feeds from the mid-2000's. 
* subpoenalty
* Europerate
* biotection
* processionalists
* apprecisioned
* innovatory
* exceptember
* reconomy
* infrastructions

## Quickstart
```
javac com/lizakowski/tools/AI/SymbolTree.java
cd com/lizakowski/tools/AI 
java  -Xmx30g -classpath ../../../../ com.lizakowski.tools.AI.SymbolTree KingJamesBible_unformatted_x2.txt
```

## Features
* Efficient data structure (custom, variable-depth Markov chain) stored in a tree format.  
* In typical usage, the Babbler looks at the world through a narrow view: just a handful of characters at a time.  Yet, it can produce reasonable-sounding text that has apparent context spanning dozens of letters, sometimes hundreds of letters.
* Output is essentially context-free.  It tends to read well, but has no meaning.  The deeper it analyzes text, the more it can appear to have meaning or context.  But there is risk of over-training - at which point it just generates the input text repeatedly.  To avoid this, the tree is evaluated at multiple depths simultaneously.  The deeper depths are given more weight, but shallow depths, including just the 1st-level letter frequencies, can still influence the output.  
* This version was generalized to analyze any stream of "Symbols", and generate a similar stream of those symbols.  These could be alphabetic letters, or any object.  UTF-8 wasn't a consideration when I wrote this, but it would be easy to make a "UTF8Symbol" class.


## What it doesn't do
* It does not analyze the structure of the input.  It cannot currently learn that open-parenthesis are followed by closed-parensthesis. So, if HTML or source code is analyzed, the results will have many syntax errors.

### Text compression?
Yes. The efficient encoding into a markov tree can be used to replace strings of letters with their tree coordinates.  This is similar to existing compression methods, but uses a different tree structure. This was tested as pre-processor for compression, and IIRC, the performance was better than gzip, but not necessarily better than bzip2.

How to compress:
For each symbol in the input file, predict the odds for that symbol being chosen.  The odds can be represented by the symbol's integer rank.  The resulting stream of rank integers can be then fed into another compression program.  The distribution will roughly resemble 1/x tending towards zero.  

Babbling can also be viewed as decompressing a file of random bytes.  The random data encodes sequences of characters that are statistically likely.  

### Identification?

When a text is compressed by the database that matches, it will compress best.  If the database mismatches, the compression will be lower.  Measuring how well it compresses can indicate if the writing style matches.

In 2006, I found an online essay-grading tool at a University.  I submitted some babbled / context-free versions of text, and the resulting text received a high score.  

<img src="ScreenshotOfUIndiana98PercentAuthentic_4_25_06.png">

## Performance
While the data structure was designed to be efficient, the current implementation holds the entire structure in RAM simultaneously.  The implementation focus was function more than performance.  

## Other areas investigated
* Generate prime numbers by learning and then emitting strings of digits?  No. There were no detecible/usable patterns in the digits.
* Identify foreign languages?   Yes.  For a long time I wondered why Google Translate didn't auto-detect language.
* Identify people?  Probably, given enough text.
* Identify words?  It does, in a way, by clustering statisticaly likely strings of symbols.  
* Learn structure and grammar?  No - that's another layer entirely.
* Mad Libs?  Spellcheck?  Yes.  The text can be predicted both backwards and forwards
* Generate high-quality palindromes?  Probably.  And this could be entertaining.
* Generate a fake news feed?  Probably. I even registered FlawedNews.com, but decided to not build the site.
* Translate bewteen languages?  No.  But it can take the output of a translator and make it sound smoother and more natural.
* Steganography?  Yes.  Generate a document using a non-random numeric sequence. Modulate the sequence with the data at a low bit-rate, then render/babble the text. OR, take hand-written text, decompose it to probabilities, then enforce a pattern on the probabilities that amounts to a subliminal channel, and re-render the text with changes.  This could be done in an interactive tool that lets the user pick words that transmit bits yet also sound appropriate.


* Generate images, which involve a second dimension?  Maybe, but it wasn't designed for this.  It is focused on 1 dimensional streams.
<img src="NeuralOut.animated_halfsize.gif">


## License
MIT