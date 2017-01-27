
(deftemplate student
	(slot study)
)

(deftemplate result
	(slot value )
)

(defrule student-rule 
	 (student (study ?s))
  =>
  	(if (eq ?s Yes) 
  		then
  		(bind ?x "Well Done! you will pass all the tests.")
 		(printout t ?x crlf)
 		(assert (result (value ?x)))

 	else 
 		(bind ?x "You should probably study more in case you want to pass your tests.")
 		(printout t ?x crlf) 
 		(assert (result (value ?x)))
 	)
)

