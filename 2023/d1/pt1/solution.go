package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
)

func main() {
	path, err := os.Getwd()
	if err != nil {
		log.Fatal(err)
	}
	file, err := os.Open(path + "/d1/input.txt")
	if err != nil {
		log.Fatal(err)
	}
	scanner := bufio.NewScanner(file)
	digits := map[string]struct{}{
		"0": {},
		"1": {},
		"2": {},
		"3": {},
		"4": {},
		"5": {},
		"6": {},
		"7": {},
		"8": {},
		"9": {},
	}
	sum := 0
	for scanner.Scan() {
		line := scanner.Text()
		lineNum := []rune{}
		for _, ch := range line {
			if _, ok := digits[string(ch)]; ok {
				lineNum = append(lineNum, ch)
			}
		}
		if len(lineNum) == 1 {
			lineNum = append(lineNum, lineNum[0])
		}
		num, err := strconv.Atoi(string([]rune{lineNum[0], lineNum[len(lineNum)-1]}))
		if err != nil {
			log.Fatal(err)
		}
		sum += num
	}
	fmt.Println(sum)
}
