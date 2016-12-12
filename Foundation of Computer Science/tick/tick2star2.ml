fun allc (x,[]) = []
  | allc (x,cs::css) = (x::cs) :: allc(x,css)

fun choose (_,[]) = []
  | choose (k,l::ls) = 
    if k = 1 
    then [l] :: choose (k,ls) 
    else (allc(l,choose (k-1,ls))) @ choose (k,ls)
