clean:
	rm -rf target

dev:
	clojure -m figwheel.main -b dev -r
