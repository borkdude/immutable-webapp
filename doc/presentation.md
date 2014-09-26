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
