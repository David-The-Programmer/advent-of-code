#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

struct coord {
    int x;
    int y;
};

int main(void) {
    char inputFilePath[] = "../input.txt";
    FILE *fp = fopen(inputFilePath, "r");
    if (fp == NULL) {
        printf("failed to read input file path :%s", inputFilePath);
        exit(EXIT_FAILURE);
    }
    int ch = 0;
    int coordLen = 1;
    struct coord *coords = malloc(coordLen * sizeof(struct coord));
    coords[0].x = 0;
    coords[0].y = 0;
    int numHouses = 1;
    int currentX = 0;
    int currentY = 0;
    while((ch = getc(fp)) != EOF) {
        if (ch == 10) {
            break;
        }
        if (ch == '>') {
            currentX++;
        } else if (ch == '<') {
            currentX--;
        } else if (ch == '^') {
            currentY--;
        } else if (ch == 'v') {
            currentY++;
        }
        bool isDup = false;
        for (int i = 0; i < coordLen; i++) {
            if(coords[i].x == currentX && coords[i].y == currentY) {
                isDup= true;
                break;
            }
        }
        if (isDup) {
            continue;
        }
        coordLen++;
        coords = realloc(coords, coordLen * sizeof(struct coord));
        coords[coordLen-1].x = currentX;
        coords[coordLen-1].y = currentY;
    }
    fclose(fp);
    free(coords);
    numHouses = coordLen;
    printf("%d houses received at least one present\n", numHouses);
}
