#include <stdio.h>
#include <stdlib.h>

int main(void) {
    char inputPath[] = "../input.txt";
    FILE *fp = fopen(inputPath, "r");
    if (fp == NULL) {
        printf("failed to read %s", inputPath);
        exit(EXIT_FAILURE);
    }
    int ch;
    int floor = 0;
    int position = 1;
    while((ch = getc(fp)) != EOF) {
        if (ch == '(') {
            floor++;
        } else if (ch == ')') {
            floor--;
        }
        if (floor == -1) {
            break;
        }
        position++;
    }
    fclose(fp);
    printf("The solution is position %d\n", position);
}
