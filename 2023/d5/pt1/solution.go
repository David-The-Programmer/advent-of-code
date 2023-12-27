package main

import (
	"bufio"
	"fmt"
	"log"
	"math"
	"os"
	"strconv"
	"strings"
)

func main() {
	path, err := os.Getwd()
	if err != nil {
		log.Fatal(err)
	}
	file, err := os.Open(path + "/d5/input.txt")
	if err != nil {
		log.Fatal(err)
	}
	scanner := bufio.NewScanner(file)
	lowestNum := math.MaxInt64
	nums := []int{}
	if scanner.Scan() {
		seedNums := strings.Split(strings.Split(scanner.Text(), ": ")[1], " ")
		for _, sn := range seedNums {
			n, err := strconv.Atoi(sn)
			if err != nil {
				log.Panic(err)
			}
			nums = append(nums, n)
		}
	}

	newNums := append([]int{}, nums...)
	for scanner.Scan() {
		line := scanner.Text()
		if line == "" {
			continue
		}
		if line[len(line)-1] == ':' {
			for i, newNum := range newNums {
				nums[i] = newNum
			}
			continue
		}
		lineNums := strings.Split(line, " ")
		destRangeStart, err := strconv.Atoi(lineNums[0])
		if err != nil {
			log.Panic(err)
		}
		srcRangeStart, err := strconv.Atoi(lineNums[1])
		if err != nil {
			log.Panic(err)
		}
		numRange, err := strconv.Atoi(lineNums[2])
		if err != nil {
			log.Panic(err)
		}
		srcRangeEnd := srcRangeStart + numRange - 1
		destRangeEnd := destRangeStart + numRange - 1
		for i, srcNum := range nums {
			if srcNum >= srcRangeStart && srcNum <= srcRangeEnd {
				newNums[i] = mapRange(srcRangeStart, srcRangeEnd, destRangeStart, destRangeEnd, srcNum)
			}
		}
	}
	for i, newNum := range newNums {
		nums[i] = newNum
	}
	for _, num := range nums {
		if lowestNum > num {
			lowestNum = num
		}
	}

	fmt.Println(lowestNum)
}

func mapRange(srcRangeStart, srcRangeEnd, destRangeStart, destRangeEnd, srcNum int) int {
	srcRange := srcRangeEnd - srcRangeStart
	destRange := destRangeEnd - destRangeStart
	return destRangeStart + (destRange/srcRange)*(srcNum-srcRangeStart)
}
