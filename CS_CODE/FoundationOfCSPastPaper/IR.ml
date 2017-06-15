fun add (a,b) = a+b
fun add a b =a+b
fun map f [] = []
	| map f (l::ls) = (f l) :: (map f ls)
fun makePair ls = map (fn x => (x,2*x+1)) ls

fun filter f [] = []
	| filter f (l::ls) = 
		if f l then l::(filter f ls)
		else filter f ls
		
fun contain x [] = false
	| contain x (l::ls) = 
		x = l orelse (contain x ls)

fun filt1 ls = filter (fn x => contain x ls) (map (fn x=> 2*x+1) ls)

datatype 'a lazyList = Nil
										 | Cons of 'a * (unit -> 'a lazyList)
										 
fun from k = Cons(k,fn () => from (k+1))
			
fun lmap f Nil = Nil
	| lmap f (Cons(x,g)) = Cons(f x, fn () => lmap f (g()))

fun lfilter f Nil = Nil
	| lfilter f (Cons(x,g)) = 
		if f x then Cons(x,fn () => lfilter f (g()))
		else lfilter f (g())
		
(*check scanned list other than the later list to avoid non-termination*)
(*is this even possible?*)

datatype 'a nwt = Lf
								| Br of 'a * ('a nwt list);
								
fun max [] = 0
	| max (x::xs) = if x > max xs then x else max xs
		
fun depth Lf = 0
	|	depth (Br(x,ls)) = 
			1 + max (map (fn x => depth x) ls)

fun maxpair a b = if a>b then b else a

fun foldl [] f c = c
	| foldl (l::ls) f c = foldl ls f (f l c)

fun depth Lf = 0
	| depth (Br(x,ls)) = 1 + (foldl (map depth ls) maxpair 0)

exception noLf

fun min [] = ~2
	| min [x] = x
	| min (l::ls) = if l < (min ls) then l else min ls
	
fun mindepth Lf = 0
	| mindepth (Br(x,[])) = ~1
	| mindepth (Br(x,ls)) = 1 + min(filter (fn y => y > ~1) (map (fn x=> depth x) ls))
	
fun min [] = raise noLf
	| min [x] = x
	| min (l::ls) = if l < (min ls) then l else min ls

fun mindepth Lf = 0
	| mindepth (Br(x,[])) = raise noLf
	| mindepth (Br(x,ls)) = 
		let fun h [] = []
					| h (x::xs) = (mindepth x) :: (h xs)
					handle noLf => h xs
		in 
				1 + min (h ls)
		end;
	
fun bfs tr = 
	let
		fun h ((n,Lf)::_) = n
			| h [] = 0
			| h ((n,Br(_,c))::ls) = h (ls @ (map (fn x => (n+1,x)) c))
			(*pattern non-exhaustive*)
	in
		h [(0,tr)]
	end;
	
fun s a b c = a c (b c)

exception oops of int

fun f x = if f [hd x] 
					then raise oops (hd x)
					else 1+(hd x) > 1
					handle oop => false
					
(*Disjoint sets*)

datatype 'a DS = Dset of 'a * ('a DS ref)
							 | Null
							 
fun find a [] = false
	| find a (x::xs) = a = x orelse find a xs

fun sameset a b (ls: ('a DS ref) list) = (find a ls) = (find b ls)
											
datatype 'a slist = Nil
									| Node of 'a * 'a slist
									
fun checkInOrder Nil = true
	| checkInOrder (Node(_,Nil)) = true
  | checkInOrder (Node(x,Node(y,ls))) = x < y andalso checkInOrder (Node(y,ls))

fun checkO [] = true
  | checkO (x::[]) = true
  | checkO (x::y::z) = !x < !y andalso checkO (y::z)
  
fun butLast [] = []
	| butLast [x] = [x]
	| butLast (x::xs) = x :: butLast xs
	
fun last [x] = x
	| last (x::xs) = last xs

fun bubblesort ls = 
	let 
		fun h [] = []
			| h [x] = [x]
			| h (x::y::xs) = 
				if !x < !y then x :: h (y::xs)
				else  y :: h (x::xs)
		fun g [] = []
			| g (xs) = g (butLast(h xs)) @ [last (h xs)]
	in 
		g ls
	end;
	
(*quick?*)
	
datatype M = Var of string
					 | Lit of int
					 | Op of string * M * M

exception noRep

fun repSub (Var(x1)) p = [(Var(x1),p)] (*whether equal?*)
	|	repSub (Op(s1,x1,y1)) (Op(s2,x2,y2)) = 
	if s1 = s2 then (repSub x1 x2) @ (repSub y1 y2)
	else raise noRep
	| repSub (Lit(x1)) (Lit(x2)) = if x1 = x2 then [] else raise noRep
	| repSub _ _ = raise noRep

(*fun checkRep =  *)

datatype 'a H = EOL
							| Cons of 'a * ('a H ref)
							
fun isMember x [] = false
	| isMember x (l::ls) = x = l orelse isMember x ls
							
fun isCyclic s =
	let 
		fun h ps (EOL) = false
			| h ps (Cons(l,ls)) = 
				(isMember ls ps) orelse (h (ls::ps) (!ls))
	in
		h ([]) s
	end;

fun f x = 1
fun f 0 = 0
	| f x = f x - 1
fun g [] xs = xs
	| g (y::ys) xs = (y::xs)
fun f xs = g (g xs []) []
fun f 1 = 0
	|	f x = f x div 2
fun f 0 = 0
	| f 100 = 0
	| f x = f x-1 + f x-1

datatype tree = Lf
							| Br of int * tree * tree

fun sum [] = 0
	| sum (x::xs) = x + sum xs
	
exception WP
							
fun check ls Lf = if sum ls > 0 then ls else raise WP
	| check ls (Br(a,t1,t2)) = check (a::ls) (t1)
		handle WP => check (a::ls) t2
		
fun getLevel _ Lf = []
	| getLevel 1 (Br(x,t1,t2)) = [x]
	| getLevel l (Br(x,t1,t2)) = (getLevel (l-1) t1) @ (getLevel (l-1) t2)

fun getL p =
	let 
		fun h l = if getLevel l p = [] then raise WP
							else if sum (getLevel l p) <= 0 then h l+1
							else l;
	in 
		h 1
	end
		



		




