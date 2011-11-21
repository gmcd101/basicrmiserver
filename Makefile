all:
	javac *.java
report: report/report.tex
	pdflatex report/report.tex
clean:
	rm *.class
