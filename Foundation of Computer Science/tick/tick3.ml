datatype 'a tree = Lf
                 | Br of 'a * 'a tree * 'a tree;

fun tcons v Lf = Br(v,Lf,Lf)
  | tcons v (Br(w,t1,t2)) = Br(v,tcons w t2, t1);

fun rtcons (Br(x,Lf,Lf)) = Lf
  | rtcons (Br(x,Br(y,y1,y2),x2)) = Br(y,x2,rtcons (Br(y,y1,y2)))

fun searcharray (Br(x,_,_),1) = x  
  | searcharray (Br(x,b1,b2),n) = 
    let val nn = n div 2 
    in if nn=0 then searcharray (b1,nn) else searcharray (b2,nn)
    end;

fun arrayoflist [] = Lf
  | arrayoflist (l::ls) = (tcons l (arrayoflist ls));

fun listofarray Lf = []
  | listofarray (Br(x,b1,b2)) = x :: listofarray (rtcons (Br(x,b1,b2))); 

fun getSubsOfEvens tr = 
    let 
        fun f (Lf,_) = []
          | f ((Br(x,x1,x2)),n) = 
            if x mod 2 = 0
            then (n :: f((rtcons (Br(x,x1,x2))),n+1))
            else f(rtcons (Br(x,x1,x2)),n+1)
    in
        f (tr,1)
    end;
