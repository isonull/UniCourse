fun replicate n x = R n x []
and R 0 (x as b) ls = ls
	| R n (x as b) ls = R (n-1) b (x::ls)

val rlist = (replicate 4 (ref 0)) @ (map ref [1, 2, 3, 4]);
val slist = map (fn r => ref (!r)) rlist;
