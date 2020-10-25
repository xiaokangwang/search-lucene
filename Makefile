SHELL := /bin/bash

all: clean createindex runqueries evaluate
	echo "Success!"
evaluate: prepare
	./../trec_eval-9.0.7/trec_eval ./data/QRelsCorrectedforTRECeval ./result.txt
runqueries: prepare
	-rm -r result.txt
	source /root/lucene-8.6.3/cp.sh && java org.kkdev.java.school.tcd.search.searcherproj.demo.SearchIndex
createindex: prepare
	source /root/lucene-8.6.3/cp.sh && java org.kkdev.java.school.tcd.search.searcherproj.demo.CreateIndex
prepare:
clean:
	-rm -r index result.txt
shell:
	bash
