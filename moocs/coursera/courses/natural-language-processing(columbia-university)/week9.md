# Unsupervised Learning: Brown Word Clustering

* Input: a (large) corpus of words
* Output 1: a partition of words into *word clusters*
* Output 2: a hierarchical word clustering (generalisation of Output 1)

### Intuition

Similar words appear in similar contexts, or, more prceisely: similar words have similar distributions of words to their immediate left and right.

### Formulation

* `V` is the set of all words seen in the corpus `w_1`, `w_2`, ..., `w_T`.

* Say `C: V -> {1, 2, ..., k}` is a *partition* of the vocabulary into `k` classes/clusters, eg `C(the) = 1`, `C(a) = 1`, `C(Monday) = 2`, `C(Friday) = 2`
* The model:
	
		p(w_1..w_n) = mult[i=1..n] e(w_i | C(w_i)) * q(C(w_i) | C(w_i-1))

	or, more conveniently:

		log p(w_1..w_n) = sum[i=1..n] log e(w_i | C(w_i)) * q(C(w_i) | C(w_i-1))

The parameters are similar to a bigram language model:

* **emission** parameter `e(w_i | C(w_i))`, eg `e(the | 1)` - probability of seeing word `the` given cluster `1`,
* **transition** parameter `q(C(w_i) | C(w_i-1))`, eg `q(3 | 1)` - probability of seeing a word in cluster `3` given previous word was in cluster `3`.

This is very similar to an HMM, except for the fact that the clustering function `C` is *deterministic*: each word is mapped to only a single cluster.

**Note:** All this machinery is here simply to let us derive `C` (which is what we're really interested in).

### A First Algorithm

* Start with |V| clusters (ie each word has its own cluster).
* Aim to find `k` final clusters
* Run `|V|-k` merge steps:
	- At each step we pick 2 clusters `c_i` and `c_j` & merge them into a single cluster
	- We greedily pick merges such that `Quality(C)` after the step is maximised

This naive algorithm takes `O(|V|^5)`. Possible to improve this to give `O(|V|^3)` but this is still too slow to be of practical use for realistic values of `|V|`.

### A Better Algorithm

* Parameter of the approach is `m` (eg `m=1000`).
* Take top `m` most frequent words, put each into its own cluster, `c_1, c_2, ..., c_m`.
* For `i=(m+1)..|V|`
	- Create a new cluster `c_m+1` for the `i`th most frequent word. We now have `m+1` clusters
	- Choose 2 clusters from `c_1..c_m+1`: pick the merge that maximises `Quality(C)`. We're now back to `m` clusters.
* Carry out `m-1` final merges to create a full hierarchy.

Running time is `O(|V|m^2 + n)` where `n` is corpus length.

