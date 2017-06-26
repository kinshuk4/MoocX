# Natural Language Processing

## Courses

- [https://www.youtube.com/playlist?list=PL6397E4B26D00A269](https://www.youtube.com/playlist?list=PL6397E4B26D00A269)[Slides](https://web.stanford.edu/~jurafsky/NLPCourseraSlides.html)

## Books

- [Artificial Intelligence - A Modern Approach 3rd Edition by Russell and Norvig, Chapter 22](https://www.google.com/?gws_rd=ssl#q=artificial+intelligence+a+modern+approach+3rd+edition+filetype:pdf)
- [Natural Language Processing With Python](https://www.google.com/?gws_rd=ssl#q=natural+language+processing+with+python+filetype%3Apdf)[Read online at nltk.org](http://www.nltk.org/book/)
- [Introduction to Information Retrieval](https://nlp.stanford.edu/IR-book/)
- [http://nbviewer.jupyter.org/github/uclmr/stat-nlp-book/blob/python/overview.ipynb](http://nbviewer.jupyter.org/github/uclmr/stat-nlp-book/blob/python/overview.ipynb)

## Tools

- [NLTK](http://www.nltk.org/)

## Vocabulary

- **Language**: A set of strings**Natural language** - A language that has evolved naturally, as opposed to a formal languageAmbiguousThere may be disagreement about which strings are part of the languageLarge and changing over time**Formal language** - "[...] a set of strings of symbols together with a set of rules that are specific to it" [[wikipedia\]](https://en.wikipedia.org/wiki/Formal_language)
- **Grammar**: Rules that specify a language
- **Semantics**: Meaning of a string
- **N-gram**: A sequence of written symbols of length n
- **Corpus**: A body of text
- **Perplexity**: Reciprocal of probability, normalized by sequence lengthPerplexity(c1:N) = P(c1:N)-1/N"[...] perplexity is a measurement of how well a probability distribution or probability model predicts a sample. It may be used to compare probability models. A low perplexity indicates the probability distribution is good at predicting the sample." [[wikipedia\]](https://en.wikipedia.org/wiki/Perplexity)
- **Morphology**: The study of morphemes**Morphemes**: The small meaningful units that make up words**Stems**: The core meaning-bearing units**Affixes**: Bits and pieces that adhere to stemsOften with grammatical functions**Stemming**: Reduce terms to their stems[Porter's algorithm](https://tartarus.org/martin/PorterStemmer/)

## Regex

- [Lecture on regular expressions](https://www.youtube.com/watch?v=hwDhO1GLb_4)
- [NLTK - Regular expressions](http://www.nltk.org/book/ch03.html#regular-expressions-for-detecting-word-patterns)

## N-gram character (or word) models

- [Lectures on n-grams and Language Modelling](https://www.youtube.com/watch?v=s3kKlUBa3b0&list=PLaRKlIqjjguC-20Glu7XVAXm6Bd6Gs7Qi)
- A probability distribution over sequences of characters: P(c1:N)
- One of the simplest language models
- A markov chain of order n - 1 [[Markov Chains Explained Visually\]](http://setosa.io/ev/markov-chains/) [[Wolfram Alpha - Markov Chain\]](http://mathworld.wolfram.com/MarkovChain.html)
- **Markov assumption**: P(ci) depends only on the N preceeding charactersP(ci|c1:i-1) = P(ci|ci - (N - 1):i - 1)P(c1:N) = 𝚷i=1:NP(ci|c1:i - 1) = 𝚷i=1:NP(ci|ci - (N - 1):i - 1))
- Applications
  - Language-identification
  - Spelling correction
  - Genre classification
  - Named-entity recognition
- Practical issues
  - Everything is done in log space
    - Avoid underflow
    - Adding is faster than multiplying
    - p1 * p2 = log(p1) + log(p2)
  - Smoothing
    - Add-1 (Laplacian) smoothing
      - Ok for text categorization, not for language modeling
    - The most commonly used method: Extended Interpolated Kneser-Ney
    - For very large N-grams: stupid backoff
- Tools
  - SRILM
  - Google N-gram corpus
  - Google Book N-gram corpus

## Sentence Segmentation

- [https://www.youtube.com/watch?v=di0N3kXfGYg&list=PL6397E4B26D00A269&index=6](https://www.youtube.com/watch?v=di0N3kXfGYg&list=PL6397E4B26D00A269&index=6)

## Edit distance

- How similar are two strings?
  - **Edit Distance**: The minimum number of editing operations to transform string A into string BInsertion, deletion, substitutionLevenshtein distance: substitutions cost 2 instead of 1
- [https://nlp.stanford.edu/IR-book/html/htmledition/edit-distance-1.html](https://nlp.stanford.edu/IR-book/html/htmledition/edit-distance-1.html)
- [https://www.youtube.com/watch?v=CXfJNzD43OI&list=PL6397E4B26D00A269&index=7](https://www.youtube.com/watch?v=CXfJNzD43OI&list=PL6397E4B26D00A269&index=7)
- [Wagner-Fischer algorithm](https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm)

## Information Extraction

- "Information extraction is the process of aquiring knowledge by skimming a text and looking for occurrences of a particular class of object and for relationships among objects." [Russel and Norvig]
- Finite-state automata
  - Eg: Regex, FASTUS
  - Work well for restricted domains
- Probabilistic models
  - Work better for noisy or high-variance data
  - Eg: [hidden Markov model (HMM)](http://mlg.eng.cam.ac.uk/zoubin/papers/ijprai.pdf)Graceful degradationCan be trained from data





https://github.com/jdsutton/AIOverview/tree/master/Natural-Language-Processing