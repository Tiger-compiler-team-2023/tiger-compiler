main : clean exemple

exemple : exemple.o
	ld -o exemple exemple.o

exemple.o : exemple.s
	as -o exemple.o exemple.s -g

clean :
	rm -f exemple.o exemple