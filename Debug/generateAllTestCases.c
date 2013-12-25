#include <stdio.h>
#include <string.h>
FILE* fp;

void swap(int i, int j, char* array)
{
	char temp = array[i];
	array[i] = array[j];
	array[j] = temp;
}

void printPermutations(int permutationSize, int origionalSize, char* word)
{

	int i;

	if(permutationSize == 0)
	{
		//reset the pointer to the initial location
		char* printer = word-origionalSize; 

		for(i=0;i<origionalSize;i++)
		{
			//print each char with a space in between
			fprintf(fp,"%c ", printer[i]);
		}

		fprintf(fp,"\n");

		return;
	}

	char temp;

	//for each letter, swap with the first character, 
	//permute the string after the first character, 
	//then swap the first character back
	for(i=0;i<permutationSize;i++)
	{
		swap(0,i,word);

		printPermutations(permutationSize-1,origionalSize,word+1);

		swap(0,i,word);
	}
}

void permuteString(char* string)
{
	//initialize with the string size
	printPermutations(strlen(string), strlen(string), string);
}

int main(int argc, char** argv)
{
	fp = fopen("allTestCases.txt", "w");
	//0-8 is all the possible moves for the tick tack toe game (when in test mode)
	char word[]= "876543210";
	//printing all permutations
	permuteString(word);
	return 0;
}