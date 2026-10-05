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


I used Design A: 

Mapper. Each call gets one line of the input, which is one document. The mapper takes the first whitespace-delimited token as the document ID .Lower-cases the rest of the line and splits it on whitespace. Strips every character that is not a–z or 0–9 and drops tokens that end up empty. Puts the cleaned tokens into a TreeSet, so each word is kept once.
It then emits:key: the document ID (Text), e.g. Document1

Reducer. For one key the reducer receives a document ID and an iterable holding that document's word string. A single document can't be compared with anything, so reduce() only splits the string back into a Set<String> and stores it in a TreeMap<String, Set<String>> documents. The TreeMap keeps the IDs in ascending String order.
The Jaccard similarity is computed in cleanup(), which Hadoop calls once after the last reduce(), when the map holds every document. cleanup() loops over every pair (i, j) with i < j, so each pair is visited once and printed in ascending ID order. For each pair it:

Builds the intersection (retainAll) and skips the pair if it is empty.
Builds the union (addAll).
Computes |A ∩ B| / |A ∪ B|.
Writes the whole line, e.g. Document1, Document2 Similarity: 0.18, as the output key with NullWritable as the value. The number is formatted with String.format(Locale.US, "%.2f", …) so the decimal point is a dot on any machine.

Driver. Beyond what L4's Controller set, I had to:

job.setNumReduceTasks(1). Design A only works if every document reaches the same reducer. With more than one, each reducer sees only part of the collection and every pair split across two reducers is silently lost.
Set the map output types separately (setMapOutputKeyClass(Text.class), setMapOutputValueClass(Text.class)). The mapper emits (Text, Text) but the job's final output is (Text, NullWritable), so Hadoop can't infer the intermediate types from the final ones.
setOutputValueClass(NullWritable.class). With the whole line in the key and a NullWritable value, TextOutputFormat writes only the key and no tab. I did not need to change mapreduce.output.textoutputformat.separator.
No combiner. L4 reused the reducer as a combiner. Here that would be wrong: a combiner runs on one mapper's partial output, so it would try to compare documents before all of them are available.

## How I ran it

The commands you used, in the order you used them. If you deviated from the steps in the
README, say where and why.

```bash

# on the Codespace
docker compose -f docker-compose.codespaces.yml up -d
mvn clean package

docker cp target/DocumentSimilarity-0.0.1-SNAPSHOT.jar resourcemanager:/tmp/
docker cp shared-folder/input/data/small_dataset.txt resourcemanager:/tmp/
docker cp shared-folder/input/data/dataset.txt resourcemanager:/tmp/
docker exec -it resourcemanager bash

# inside the container
cd /tmp
hadoop fs -mkdir -p /input/data
hadoop fs -put ./small_dataset.txt /input/data
hadoop fs -put ./dataset.txt /input/data
hadoop fs -ls /input/data

# the first runs produced wrong output (see Problems and fixes), so before rerunning:
hadoop fs -rm -r /output/small_dataset /output/dataset

hadoop jar /tmp/DocumentSimilarity-0.0.1-SNAPSHOT.jar \
  com.example.controller.DocumentSimilarityDriver /input/data/small_dataset.txt /output/small_dataset
hadoop fs -cat /output/small_dataset/*

hadoop jar /tmp/DocumentSimilarity-0.0.1-SNAPSHOT.jar \
  com.example.controller.DocumentSimilarityDriver /input/data/dataset.txt /output/dataset
hadoop fs -cat /output/dataset/*

hdfs dfs -get /output /tmp/
exit

# back on the Codespace
docker cp resourcemanager:/tmp/output/. shared-folder/output/
docker compose -f docker-compose.codespaces.yml down -v

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

Most similar pairs are : Doc08–Doc11 (0.22), then Doc02–Doc03 and Doc05–Doc06 (both 0.20). Least similar: Doc02–Doc09 (0.05), Doc02–Doc07 (0.06), Doc02–Doc11 (0.06).

The top pairs make sense because each one covers the same topic.

The values are low and close together because the documents are short (32–41 distinct words), and common words like "the", "a" and "and" are shared by almost every pair. Removing stop words during tokenization would fix this: unrelated pairs drop to 0, and the related ones stand out.
---

## Scalability

**If you used Design A:** it relies on a single reducer that holds every document in memory.
What concretely breaks when the collection has a million documents? Sketch how Design B
avoids the problem.

**If you used Design B:** why did it need more than one pass (or how did you avoid that)?
What is its own bottleneck?

With a million documents, one reducer would have to hold every word set in memory. That is tens of GB, so it would fail with OutOfMemoryError or be killed by YARN. It would also have to compare about 5 × 10¹¹ pairs in a single task while the rest of the cluster sits idle.

Design B makes the word the key, so the work is spread over many reducers and each one only holds the list of documents for one word. A second job then sums the shared-word counts per pair and computes Jaccard as |A∩B| / (|A| + |B| − |A∩B|).
---

## Problems and fixes

Anything that went wrong and what resolved it. Paste the actual error message. If nothing
went wrong, say so.

The first build failed with compile errors:
[ERROR] DocumentSimilarityMapper.java:[49,9] cannot find symbol  symbol: class TreeSet
[ERROR] DocumentSimilarityReducer.java:[47,44] cannot find symbol  symbol: variable documents
[ERROR] DocumentSimilarityReducer.java:[71,63] incompatible types: NullWritable cannot be converted to Text

I fixed them by adding the TreeSet import, declaring the documents map, writing the reduce() body, and changing the Reducer's output value type to NullWritable. My first output also just repeated the input lines, so I rebuilt the JAR, deleted the old output with hadoop fs -rm -r /output/*, and reran both jobs.
---

## Use of generative AI

If you used a generative AI tool, include the acknowledgment statement from the syllabus and
say specifically what you used it for. If you did not use one, say so.

I used Claude (Anthropic) during this assignment, for three things:

1) Diagnosing the mvn clean package compilation errors above and fixing the Reducer.
2) Explaining why the first output contained the input lines.
3) Helping draft the text of this report. 

I checked the final code by building it and running both jobs on the cluster myself, and I compared the small-dataset output with the expected result in the README. I reviewed every section of this report against my own code and output.


