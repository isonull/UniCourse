datatype 'a tree = Lf
                 | Br of 'a * 'a tree * 'a tree;
fun insert (str : string,Lf) = Br(str,Lf,Lf)
  | insert (str,Br(x,b1,b2)) = 
    if str < x 
    then Br(x,insert (str,b1),b2)
    else if str > x 
         then Br(x,b1,insert (str,b2))
         else Br(x,b1,b2);

fun member (str : string,Lf) = false
  | member (str,Br(x,b1,b2)) = str = x orelse member (str,b1) orelse member (str,b2);

fun union (res,Lf) = res
  | union (Lf,res) = res
  | union (Br(x,x1,x2),res) = union(x1,union(x2,insert(x,res)));

fun inter_ (Lf,_,res) = res
  | inter_ (Br(x,x1,x2),tr,res) =
      if member(x,tr) 
      then union(inter_(x1,tr,insert(x,res)),inter_(x2,tr,insert(x,res)))
      else union(inter_(x1,tr,res),inter_(x2,tr,res));

fun inter (a,b) = inter_(a,b,Lf);

fun max (Br(x,_,Lf)) = x
  | max (Br(x,b1,b2)) = max b2 

fun delmax (Br(x,x1,Lf)) = x1
  | delmax (Br(x,x1,x2)) = Br(x,x1,delmax x2)
  | delmax Lf = Lf

exception noEle

fun remove (str,Lf) = Lf
  | remove (str,Br(x,Lf,Lf)) = if str = x then Lf else Br(x,Lf,Lf) 
  | remove (str,Br(x,b1,Lf)) = if x= str then b1 else Br(x,remove(str,b1),Lf)
  | remove (str,Br(x,Lf,b2)) = if x= str then b2 else Br(x,Lf,remove(str,b2))
  | remove (str : string,Br(x,b1,Br(y,c1,c2))) = 
    if x = str 
    then 
        if c1 <> Lf 
        then Br(y,b1,Br(max c1,delmax c1,c2))
        else Br(y,b1,c2)
    else 
        if x < str then Br(x,b1,remove (str,Br(y,c1,c2)))
        else Br(x,remove(str,b1),Br(y,c1,c2));

