fun choose (k,l::ls) =
    let
        fun ch (k,[],ll) = (if k = 0 then [rev ll] else [])
          | ch (k,l::ls,ll) = 
            if k = 0 then [rev ll]
            else ch (k-1,ls,l::ll) @ ch (k,ls,ll)
    in
        ch (k,l::ls,[])
    end;
