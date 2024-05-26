#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <ctype.h>
#include <string.h>

#define GRID_MAX_ROWS 1000
#define GRID_MAX_COLS 1000
#define ACTION_MAX_LEN 8


int main(void) {
    char inputFilePath[] = "../input.txt";
    FILE *fp = fopen(inputFilePath, "r");
    if (fp == NULL) {
        printf("failed to read input file path: %s", inputFilePath);
        exit(EXIT_FAILURE);
    }
    int grid[GRID_MAX_ROWS][GRID_MAX_COLS];
    for (int i = 0; i < GRID_MAX_ROWS; i++) {
        for (int j = 0; j < GRID_MAX_COLS; j++) {
            grid[i][j] = 0;
        }
    }
    int x1, y1, x2, y2;
    char action[ACTION_MAX_LEN];
    while(fscanf(fp, "%[^0123456789] %d,%d through %d,%d\n", action, &x1, &y1, &x2, &y2) != EOF) {
        char a = action[strlen(action)-2];
        if(a == 'n') {
            // turn on, increase brightness
            for (int r = y1; r <= y2; r++) {
                for (int c = x1; c <= x2; c++) {
                    grid[r][c]++;
                }
            }
        } else if (a == 'f') {
            // turn off, decrease brightness, cannot go below 0
            for (int r = y1; r <= y2; r++) {
                for (int c = x1; c <= x2; c++) {
                    if (grid[r][c] > 0) {
                        grid[r][c]--;
                    }
                }
            }
        } else if (a == 'e') {
            // toggle, increase brightness by 2
            for (int r = y1; r <= y2; r++) {
                for (int c = x1; c <= x2; c++) {
                    grid[r][c] += 2;
                }
            }
        }
    }
    int brightness = 0;
    for (int i = 0; i < GRID_MAX_ROWS; i++) {
        for (int j = 0; j < GRID_MAX_COLS; j++) {
            brightness += grid[i][j];
        }
    }
    fclose(fp);
    printf("Total brightness is %d\n", brightness);
}
