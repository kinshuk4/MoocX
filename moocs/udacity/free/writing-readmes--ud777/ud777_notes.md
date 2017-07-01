Writing READMEs
https://www.udacity.com/course/viewer#!/c-ud777

17/3/2016

READMEs are written for humans. 

When we code we know what we are doing but when we look back and see why we did it we may not remember it correctly.

<u>Lesson 7 : Anatomy of a README</u>

Example Taken - github/ledbetter

Following is the readme contains here:

1. Language provides the just enough context to get the user up and running with the code.
2. It contains - A Title, a Description(a sentence or 2 are fine but capture the spirit of the project clearly and precisely. ), a Logo(optional) if you are feeling extra fancy.
3. Installation instruction mentioning build or depedencies or you can mention limitations, known bugs etc. An installation section can be popular.
   Make no assumptions and be clear.

So here are necessary part:

Information: They explain more or everything about the package
Structure: Headings, links, snipets of code and a licence are present,
Patterns: There is one per package

<u>Things to answer in a README</u>
What steps need to be taken?
What should the user already have installed or configured?
What might they have a hard time understanding right away?

Things in a README
https://www.udacity.com/course/viewer#!/c-ud777/l-5338568539/m-5331778616
Title & description - capture spirit of project, possibly logo?


https://www.udacity.com/course/viewer#!/c-ud777/l-5338568539/e-5331778617/m-5331778618
Include a Contributing section and also then include copyright licence e.g. http://choosealicense.com/
Only a new info is needed, keep it simple e.g. known bugs, FAQ, table of contents

Allowing others to contribute can be added in the readme. 

Shields - Tells the code status whether the build is passing or not.

More sections:

- Known bugs
- Frequently asked questions (if you are getting asked same question again and again)
- Table of contents

### Markdown 101 - Readable READMEs with Markdown

Markdown is a light markup language often used for READMEs (though you'll find other use cases for it, too!). It is fairly straightforward, and much of the syntax is intuitive.



Bold: To make text bold, surround it with double asterisks, e.g. Isn't today a **wonderful** day?
Italics: To italicize text, surround it with underscores e.g. And in that moment I thought to myself: _Did I turn off the stove?_
Inline code: For this, you'll surround text in backticks (``, not a single quote), e.g. You should use the `strong` tag.

The Title Sequence: For h1 through h6 tags, all you'll need is a # before your text. The number of #s you include tells Markdown which header tag you're using. For example:
## This is a header.
### This is another header.

[links](url address)

* bullet point

> quoting text / inline text (this is a little different to quoting code as font changed)



```     quoting a block of code (end with another ```)

summary found at : https://help.github.com/articles/basic-writing-and-formatting-syntax/

online markdown viewer found at: https://jbt.github.io/markdown-editor/

========================================
[start of sample markdown exercise]

# My Fabulous Recipe

This recipe for ** cereal and milk ** has been passed down my family for months.

## Ingredients

* Cereal (you can find cool cereals [here](www.example.com/coolcereals)
* Milk

## Directions

If I were writing these out as _code_, it might look something like this:

```
if bowl is empty: 
    add cereal  
if bowl only has cereal in it:  
    add milk
```
[end of sample markdown exercise]
======================================