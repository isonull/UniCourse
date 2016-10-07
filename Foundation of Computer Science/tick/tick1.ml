fun evalquad(a,b,c,x) : real = a*x*x+b*x+c;
fun facr(0) = 1
  | facr(n) = n * facr(n-1);
fun faci(n) = 
    let 
        fun facii(t,a) = if t = 0 then a else facii(t-1,a*t)
    in 
        facii(n,1)
    end;
fun sumt(n) = 
    let 
        fun f(x,0) = 0.0
          | f(x,n) = x + f(x/2.0,n-1)
    in 
        f(1.0,n)
    end;

