package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Reducer for the document similarity job.
 *
 * Input:  whatever your mapper emits, grouped by key by the shuffle/sort phase.
 *
 * Output: one line per pair of documents that share at least one word, in exactly this
 *         format (see README.md):
 *
 *             Doc01, Doc02 Similarity: 0.18
 *
 *         where the two IDs are in ascending String order (Doc01 before Doc02), and the
 *         Jaccard similarity  |A ∩ B| / |A ∪ B|  is printed with two decimals, e.g.
 *         String.format("%.2f", similarity). Note that "%.2f" uses the machine's locale;
 *         use  String.format(java.util.Locale.US, "%.2f", similarity)  to be safe.
 *
 * Hint: in the design suggested in README.md all documents reach a single reducer, one per
 *       reduce() call. You cannot compare documents until you have seen all of them, so
 *       reduce() only stores each document, and the pairwise comparison happens in
 *       cleanup(), which Hadoop calls once after the last reduce() call.
 */
public class DocumentSimilarityReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {
        // TODO
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        // TODO (only needed if your design compares documents here)
        List<String> ids = new ArrayList<>(documents.keySet());

        for (int i = 0; i < ids.size(); i++) {
            for (int j = i + 1; j < ids.size(); j++) {
                String idA = ids.get(i);
                String idB = ids.get(j);

                Set<String> a = documents.get(idA);
                Set<String> b = documents.get(idB);

                Set<String> intersection = new HashSet<>(a);
                intersection.retainAll(b);

                if (intersection.isEmpty()) {
                    continue;
                }

                Set<String> union = new HashSet<>(a);
                union.addAll(b);

                double similarity = (double) intersection.size() / union.size();
                String line = String.format(Locale.US, "%s, %s Similarity: %.2f",
                        idA, idB, similarity);

                context.write(new Text(line), NullWritable.get());
            }
        }
    }
}
