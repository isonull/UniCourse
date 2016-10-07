fun eapprox(t : int) = 
    let
        fun f(sum,ai,n,0)   = sum
          | f(sum,ai,n,lim) = f(sum+ai,ai/n,n+1.0,lim-1)

    in 
        f(0.0,1.0,1.0,t)
    end;
fun exp(z,n) = 
    let 
        fun f(sum,ai,n,z,0)   = sum 
          | f(sum,ai,n,z,lim) = f(sum+ai,ai/n*z,n+1.0,z,lim-1)
    in
        f(0.0,1.0,1.0,z,n)
    end;
fun gcd(a,b) =  if a = b then a

                else if a mod 2 = 0 andalso b mod 2 = 0 then 
                    2*gcd(a div 2,b div 2)
                else if a mod 2 = 1 andalso b mod 2 = 0 then 
                    gcd(a,b div 2)
                else if a mod 2 = 0 andalso b mod 2 = 1 then
                    gcd(a div 2,b)
                else gcd(b, abs(a - b) div 2)

