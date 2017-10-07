(ns koans.15-destructuring
  (:require [koan-engine.core :refer :all]))

(def test-address
  {:street-address "123 Test Lane"
   :city "Testerville"
   :state "TX"})

(meditations
 "Destructuring is an arbiter: it breaks up arguments"
 (= ":bar:foo" ((fn [[a b]] (str b a))
                [:foo :bar]))

 "Whether in function definitions"
 (= (str "An Oxford comma list of apples, "
         "oranges, "
         "and pears.")
    ((fn [[a b c]] (str "An Oxford comma list of " a ", " b ", and " c "."))
     ["apples" "oranges" "pears"]))

 "Or in let expressions"
 (= "Rich Hickey aka The Clojurer aka Go Time aka Lambda Guru"
    (let [[first-name last-name & aliases]
          (list "Rich" "Hickey" "The Clojurer" "Go Time" "Lambda Guru")
          aka " aka "
          remaining (conj
                     (mapv #(str % aka) (butlast aliases))
                     (last aliases))]
      (str first-name " " last-name aka (clojure.string/join "" remaining))))

 "You can regain the full argument if you like arguing"
 (= {:original-parts ["Stephen" "Hawking"] :named-parts {:first "Stephen" :last "Hawking"}}
    (let [[first-name last-name :as full-name] ["Stephen" "Hawking"]]
      (->> (assoc {} :first first-name :last last-name)
           (assoc {} :original-parts full-name :named-parts))))

 "Break up maps by key"
 (= "123 Test Lane, Testerville, TX"
    (let [{street-address :street-address, city :city, state :state} test-address]
      (clojure.string/join ", " [street-address city state])))

 "Or more succinctly"
 (= "123 Test Lane, Testerville, TX"
    (let [{:keys [street-address city state]} test-address]
      (str street-address ", " city ", " state)))

 "All together now!"
 (= "Test Testerson, 123 Test Lane, Testerville, TX"
    (let [[first-name last-name] ["Test" "Testerson"]
          {:keys [street-address city state]} test-address]
      (str first-name " " last-name ", " (clojure.string/join ", " [street-address
                                                                    city
                                                                    state])))))
