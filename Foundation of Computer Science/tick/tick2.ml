fun last (l::[]) = l
  | last (l::ls) = last ls;

fun butlast (l::[]) = nil
  | butlast (l::ls) = l :: butlast ls;
(*space complexity:O(n)
  time  complexity:o(n)
  compare with rev(tl(rev xs))
  space complexity: the same
  time  comolexity: butlast faster (coefficient of 0.5)
*)

fun nth (l::ls,0) = l 
  | nth (l::ls,n) = nth (ls,n-1);

