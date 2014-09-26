.notes landslide gastcollege.md --linenos=inline -c -r

# The Immutable Stack
04-10-2014

Martin van Amersfoorth

Michiel Borkent

![finalist](img/logo.png)

---
# Schedule

* Intro (10 m.)
* Clojure crash course (20 m.)
* Web server (10+20 m.)
* Web client (10+20 m.)
* Database (10+20 m.)

---
# Intro

* Clojure
* Clojurescript
* Web techologies
** Ring, Compojure, Liberator
** React
* Datomic

---
# Clojure crash course

# REPL
- Interactive development: Read Eval Print Loop

		!clojure
		user=>                         <- prompt

- Start one by `lein repl`
  or in IntelliJ

---
# Clojure crash course

In the REPL I will cover the following basics:

* Clojure basics and special forms
    - if
    - let
    - function call
    - function definition


---
# Clojure crash course

# If

    !clojure
    (if (< (rand-int 10) 5)
      "Smaller than 5"
      "Greater or equal than 5")

---
# Clojure crash course

# Let
    !clojure
    (let [x (+ 1 2 3)
          y (+ 4 5 6)
          z (+ x y)]
      z) ;;=> 21

---
# Clojure crash course

# Function call

    !clojure
    (inc 1) ;;=> 2

instead of

    !javascript
    inc(1) // 2

It is called prefix notation

---
# Clojure crash course

# Function definition
    !clojure
    (def my-fun (fn [x]
                  (+ x 2)))

    ;; same as:
    (defn my-fun [x]
      (+ x 2))

---
# Clojure crash course

Literals, symbols and keywords

    !clojure
    1     ;; integer literal
    "foo" ;; string literal
    'foo  ;; quoted symbol
    foo   ;; symbol (will evaluate to value bound to foo)
    :foo  ;; keyword, more or less a constant, often used as key in hashmap
    {:a 1, :b 2} ;; map literal

---
# Clojure crash course

;; TODO

* Clojure collections
    - vectors
    - maps
    - lists
    - sets

* Functions on data structures

---
# Mutable state

Atoms are mutable references to immutable values

Pure functions are used to transform immutable value

    !clojure
    (def game-state (atom {:score 0}))

    (defn increase-score [old-state points]
    (update-in old-state [:score] + points))

    ;; test:
    (increase-score {:score 40} 20) ;;=> {:score 60}

    (defn score! []
      (swap! game-state increase-score 20))

    @game-state ;;=> {:score 0}
    (score!)
    @game-state ;;=> {:score 20}
    (score!)
    @game-state ;;=> {:score 40}


---


---
# Clojure crash course

Use the [Clojure cheat sheet](http://clojure.org/cheatsheet)


# Voorbeeld parallellisatie
    !clojure
    (defn reverse-str [s]
      (apply str
             (reverse s)))

    (reverse-str "foo") ;;=> "oof"


Sequentieel

    !clojure
    (map reverse-str ["foo" "bar" "baz"])
    ;;=> ("oof" "rab" "zab")


Parallel (1 letter verschil)

    !clojure
    (pmap reverse-str ["foo" "bar" "baz"])
    ;;=> ("oof" "rab" "zab")


---
# Hogere orde functie
1. Functie die een of meer functies als invoer heeft
2. Of: functie die een andere functie oplevert (komen we vandaag niet
   aan toe)

---
# Hogere orde functie: map
    !clojure
    (map inc [1 2 3]) ;;=> (2 3 4)


- Invoer-functie is hier `inc`
- Past invoerfunctie toe op elk element in een collectie.
- Levert een nieuwe collectie op.

---
# Hogere orde functie: filter
    !clojure
    (odd? 1) ;;=> true
    (odd? 2) ;;=> false
    (range 10) ;;=> (0 1 2 3 4 5 6 7 8 9)
    (filter odd? (range 10)) ;;=> (1 3 5 7 9)


- Invoer-functie is hier `odd?`
- filtert de elementen uit een collectie waarvoor functie 'logisch
  waar' oplevert
  (In Clojure is alles behalve `nil` en `false` logisch waar)

---
# Hogere orde functie: reduce
    !clojure
    (reduce + [1 2 3 4 5]) ;;=> 15


Stappen:

    !clojure
    (reduce + [1 2 3 4 5])
    (+ 1 2) ;;=> 3
    (reduce + [3 3 4 5])
    (+ 3 3 ) ;;=> 6
    (reduce + [6 4 5])
    (+ 6 4) ;;=> 10
    (reduce + [10 5])
    (+ 10 5) ;;=> 15
    15

---

# Datalog in 6 minutes

---

# Query Anatomy

Clojure

	!clojure
	(q ('[:find ...
	      :in ...
	      :where ...]
	      input1
	      ...
	      inputN))
	
.notes: :where - constraints, :in - inputs, :find - variables to return
	
---

# Variables and Constants

Variables

* ?customer
* ?product
* ?orderId
* ?email
	
Constants

* 42
* :email
* "john"
* :order/id
* \#instant "2012-02-29"

---

# Data Pattern: E-A-V

	!html
	-------------------------------------------
	| entity | attribute | value              |
	-------------------------------------------
	| 42     | :email	  | jdoe@example.com  |
	| 43     | :email     | jane@example.com  |
	| 42     | :orders    | 107               |
	| 42     | :orders    | 141               |
	-------------------------------------------

Constrain the results returned, binds variables

	!clojure
	[?customer :email ?email]
-> jdoe@example.com, jane@example.com

	!clojure
	[42 :email ?email]
-> jdoe@example.com
	
---

# Data Pattern: E-A-V

	!html
	-------------------------------------------
	| entity | attribute | value              |
	-------------------------------------------
	| 42     | :email	  | jdoe@example.com  |
	| 43     | :email     | jane@example.com  |
	| 42     | :orders    | 107               |
	| 42     | :orders    | 141               |
	-------------------------------------------

What attributes does customer 42 have?

	!clojure
	[42 ?attribute]
-> :email, :orders

What attributes and values does customer 42 have?

	!clojure
	[42 ?attribute ?value]
-> :email - jdoe@example.com, :orders - 107, 141

--- 

# Where Clause

Where to put the data pattern?

	!clojure
	[:find ?customer
	 :where [?customer :email]]
	
Implicit Join

	!clojure
	[:find ?customer
	 :where [?customer :email]
	        [?customer :orders]]

---

# Input(s)

	!clojure
	(q '[:find ?customer :in $ :where [?customer :id] [?customer :orders]]
       db)
	
Find using $database and ?email:

	!clojure
	(q '[:find ?customer :in $ ?email :where [?customer :email ?email]]
	    db "jdoe@example.com")
	
---

# Predicates

Functional constraints that can appear in a :where clause

	!clojure
	[(< 50.0 ?price)]
	
Find the expensive items

	!clojure
	[:find ?item
	 :where [?item :item/price ?price]
	        [(< 50.0 ?price)]]
	
---

# Aggregates

The syntax is incorporated in the :find clause:

    !clojure
    [:find ?a (min ?b) (max ?b) ?c (sample 12 ?d) :where ...]

The list expressions are aggregate expressions. 
Query variables not in aggregate expressions will group the results and appear intact in the result. 

The included aggregation functions are:

* min, max
* count, count-distinct
* sum, avg, median
* variance, stddev
* rand
	
---

# Functions

	!clojure
	[(shipping ?zip ?weight) ?cost]
	
Call functions by binding inputs:

	!clojure
	[:find ?customer ?product
	 :where [?customer :shipAddress ?address]
	        [?address :zip ?zip]
	        [?product :product/weight ?weight]
	        [?product :product/price ?price]
	        [(Shipping/estimate ?zip ?weight) ?shipCost]
	        [(<= ?price ?shipCost)]]
	
Or: find me the customer/product combinations where the shipping cost dominates the product cost.
