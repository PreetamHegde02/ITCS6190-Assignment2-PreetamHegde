# Assignment #2 — Report

**Name: *Preetam Manjunath Hegde*
**Student ID: *801496859*
**Email: *phegde4@charlotte.edu*

---

## Design

Which design did you choose (A, B, or your own)? Explain in your own words:

- What your **Mapper** emits as key and value, and why that is the right thing to emit.
- What your **Reducer** receives for one key, what it does with it, and where the Jaccard
  similarity is computed.
- What you had to set in the **Driver** beyond what L4's `Controller` set, and why.



---

## How I ran it

The commands you used, in the order you used them. If you deviated from the steps in the
README, say where and why.

```bash

```

---

## Output

### `small_dataset.txt` (3 lines)

Document1, Document2 Similarity: 0.18
Document1, Document3 Similarity: 0.20
Document2, Document3 Similarity: 0.10

### `dataset.txt` (66 lines)

Doc01, Doc02 Similarity: 0.16
Doc01, Doc03 Similarity: 0.13
Doc01, Doc04 Similarity: 0.07
Doc01, Doc05 Similarity: 0.10
Doc01, Doc06 Similarity: 0.09
Doc01, Doc07 Similarity: 0.11
Doc01, Doc08 Similarity: 0.10
Doc01, Doc09 Similarity: 0.11
Doc01, Doc10 Similarity: 0.09
Doc01, Doc11 Similarity: 0.07
Doc01, Doc12 Similarity: 0.19
Doc02, Doc03 Similarity: 0.20
Doc02, Doc04 Similarity: 0.13
Doc02, Doc05 Similarity: 0.10
Doc02, Doc06 Similarity: 0.09
Doc02, Doc07 Similarity: 0.06
Doc02, Doc08 Similarity: 0.09
Doc02, Doc09 Similarity: 0.05
Doc02, Doc10 Similarity: 0.10
Doc02, Doc11 Similarity: 0.06
Doc02, Doc12 Similarity: 0.14
Doc03, Doc04 Similarity: 0.17
Doc03, Doc05 Similarity: 0.11
Doc03, Doc06 Similarity: 0.08
Doc03, Doc07 Similarity: 0.16
Doc03, Doc08 Similarity: 0.11
Doc03, Doc09 Similarity: 0.07
Doc03, Doc10 Similarity: 0.10
Doc03, Doc11 Similarity: 0.12
Doc03, Doc12 Similarity: 0.11
Doc04, Doc05 Similarity: 0.09
Doc04, Doc06 Similarity: 0.11
Doc04, Doc07 Similarity: 0.18
Doc04, Doc08 Similarity: 0.09
Doc04, Doc09 Similarity: 0.08
Doc04, Doc10 Similarity: 0.10
Doc04, Doc11 Similarity: 0.09
Doc04, Doc12 Similarity: 0.09
Doc05, Doc06 Similarity: 0.20
Doc05, Doc07 Similarity: 0.14
Doc05, Doc08 Similarity: 0.15
Doc05, Doc09 Similarity: 0.07
Doc05, Doc10 Similarity: 0.13
Doc05, Doc11 Similarity: 0.14
Doc05, Doc12 Similarity: 0.11
Doc06, Doc07 Similarity: 0.17
Doc06, Doc08 Similarity: 0.15
Doc06, Doc09 Similarity: 0.08
Doc06, Doc10 Similarity: 0.10
Doc06, Doc11 Similarity: 0.12
Doc06, Doc12 Similarity: 0.13
Doc07, Doc08 Similarity: 0.15
Doc07, Doc09 Similarity: 0.07
Doc07, Doc10 Similarity: 0.08
Doc07, Doc11 Similarity: 0.12
Doc07, Doc12 Similarity: 0.11
Doc08, Doc09 Similarity: 0.19
Doc08, Doc10 Similarity: 0.13
Doc08, Doc11 Similarity: 0.22
Doc08, Doc12 Similarity: 0.12
Doc09, Doc10 Similarity: 0.13
Doc09, Doc11 Similarity: 0.12
Doc09, Doc12 Similarity: 0.13
Doc10, Doc11 Similarity: 0.12
Doc10, Doc12 Similarity: 0.12
Doc11, Doc12 Similarity: 0.11

## Analysis

Look at the results for `dataset.txt`.

- Which pairs are the most similar, and which the least?
- Do the most similar pairs make sense given what the documents are about?
- The values are all fairly low and close together. Why? What one change to the tokenization
  rules would make the numbers more meaningful?



---

## Scalability

**If you used Design A:** it relies on a single reducer that holds every document in memory.
What concretely breaks when the collection has a million documents? Sketch how Design B
avoids the problem.

**If you used Design B:** why did it need more than one pass (or how did you avoid that)?
What is its own bottleneck?



---

## Problems and fixes

Anything that went wrong and what resolved it. Paste the actual error message. If nothing
went wrong, say so.



---

## Use of generative AI

If you used a generative AI tool, include the acknowledgment statement from the syllabus and
say specifically what you used it for. If you did not use one, say so.


