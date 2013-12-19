#include <stdio.h>
#include <string.h>

void swap(int i, int j, char* array)
{
	char temp = array[i];
	array[i] = array[j];
	array[j] = temp;
}

void printPermutations(int size, int origionalSize, char* word)
{

	int i;
	if(size == 0)
	{
		char* printer = word-origionalSize; 
		for(i=0;i<origionalSize;i++)
		{
			printf("%c ", printer[i]);
		}
		printf("\nyes\n");
		return;
	}
	char temp;
	for(i=0;i<size;i++)
	{
		swap(0,i,word);
		//printf("%c",word[0]);
		printPermutations(size-1,origionalSize,word+1);
	}
}

int main(int argc, char** argv)
{
	char word[]= "876543210";
	int length = strlen(word);
	int i=0;
	printPermutations(length, length,word);
	printf("%d\n",i);
	return 0;
}