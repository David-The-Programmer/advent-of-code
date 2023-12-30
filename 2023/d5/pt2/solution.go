package main

import (
	"bufio"
	"fmt"
	"log"
	"math"
	"os"
	"sort"
	"strconv"
	"strings"
)

func main() {
	path, err := os.Getwd()
	if err != nil {
		log.Panic(err)
	}
	file, err := os.Open(path + "/d5/input.txt")
	if err != nil {
		log.Panic(err)
	}
	scanner := bufio.NewScanner(file)
	seedNums := []string{}
	if scanner.Scan() {
		seedNums = strings.Split(strings.Split(scanner.Text(), ": ")[1], " ")
	}
	seedRanges := [][]int{}
	for i := 0; i < len(seedNums); i += 2 {
		rangeStart, err := strconv.Atoi(seedNums[i])
		if err != nil {
			log.Panic(err)
		}
		rangeLength, err := strconv.Atoi(seedNums[i+1])
		if err != nil {
			log.Panic(err)
		}
		seedRanges = append(seedRanges, []int{rangeStart, rangeStart + rangeLength - 1})
	}

	srcRanges := [][]int{}
	destRanges := [][]int{}

	for scanner.Scan() {
		line := scanner.Text()
		if line == "" {
			continue
		}
		if line[len(line)-1] == ':' && len(srcRanges) == 0 {
			continue
		}
		if line[len(line)-1] != ':' {
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
			srcRanges = append(srcRanges, []int{srcRangeStart, srcRangeEnd})
			destRanges = append(destRanges, []int{destRangeStart, destRangeEnd})
			continue
		}

		splitSeedRanges := [][]int{}
		for _, seedRange := range seedRanges {
			splitSeedRanges = append(splitSeedRanges, splitSeedRange(seedRange, srcRanges)...)
		}
		// map all of the split seed ranges to dest ranges
		for i := range splitSeedRanges {
			for j := range srcRanges {
				srcStart := srcRanges[j][0]
				srcEnd := srcRanges[j][1]
				destStart := destRanges[j][0]
				destEnd := destRanges[j][1]

				if splitSeedRanges[i][0] >= srcStart && splitSeedRanges[i][1] <= srcEnd {
					splitSeedRanges[i][0] = mapRange(srcStart, srcEnd, destStart, destEnd, splitSeedRanges[i][0])
					splitSeedRanges[i][1] = mapRange(srcStart, srcEnd, destStart, destEnd, splitSeedRanges[i][1])
					break
				}
			}
		}
		seedRanges = append([][]int{}, splitSeedRanges...)

		srcRanges = [][]int{}
		destRanges = [][]int{}
	}

	splitSeedRanges := [][]int{}
	for _, seedRange := range seedRanges {
		splitSeedRanges = append(splitSeedRanges, splitSeedRange(seedRange, srcRanges)...)
	}
	// map all of the split seed ranges to dest ranges
	for i := range splitSeedRanges {
		for j := range srcRanges {
			srcStart := srcRanges[j][0]
			srcEnd := srcRanges[j][1]
			destStart := destRanges[j][0]
			destEnd := destRanges[j][1]

			if splitSeedRanges[i][0] >= srcStart && splitSeedRanges[i][1] <= srcEnd {
				splitSeedRanges[i][0] = mapRange(srcStart, srcEnd, destStart, destEnd, splitSeedRanges[i][0])
				splitSeedRanges[i][1] = mapRange(srcStart, srcEnd, destStart, destEnd, splitSeedRanges[i][1])
				break
			}
		}
	}
	seedRanges = append([][]int{}, splitSeedRanges...)

	lowestNum := math.MaxInt64
	for _, seedRange := range seedRanges {
		num := seedRange[0]
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

// Credit to this post: https://stackoverflow.com/questions/4322649/range-splitting-problem
func splitSeedRange(seedRange []int, srcRanges [][]int) [][]int {

	splitSeedRanges := [][]int{}
	seedRangeStart := seedRange[0]
	seedRangeEnd := seedRange[1]

	type rangeNum struct {
		value   int
		isStart bool
	}
	rangeNums := []rangeNum{
		{
			value:   seedRangeStart,
			isStart: true,
		},
		{
			value:   seedRangeEnd,
			isStart: false,
		},
	}
	dups := map[int]struct{}{}
	for _, r := range srcRanges {
		rangeNums = append(rangeNums, rangeNum{
			value:   r[0],
			isStart: true,
		})
		rangeNums = append(rangeNums, rangeNum{
			value:   r[1],
			isStart: false,
		})
	}
	sort.Slice(rangeNums, func(i, j int) bool {
		return rangeNums[i].value < rangeNums[j].value
	})
	// remove duplicates
	noDupsRangeNums := []rangeNum{}
	for _, rNum := range rangeNums {
		if _, ok := dups[rNum.value]; ok {
			continue
		}
		dups[rNum.value] = struct{}{}
		noDupsRangeNums = append(noDupsRangeNums, rNum)
	}
	rangeNums = append([]rangeNum{}, noDupsRangeNums...)

	numUnendedRanges := 0
	rangeStart := -1
	splitRanges := [][]int{}
	for _, rangeNum := range rangeNums {
		if rangeNum.isStart {
			numUnendedRanges++
		} else {
			numUnendedRanges--
		}
		if rangeStart == -1 {
			rangeStart = rangeNum.value
			continue
		}
		if rangeNum.isStart {
			rangeEnd := rangeNum.value - 1
			if rangeEnd < rangeStart {
				rangeEnd = rangeStart
			}
			splitRanges = append(splitRanges, []int{rangeStart, rangeEnd})
			rangeStart = rangeNum.value
		} else {
			splitRanges = append(splitRanges, []int{rangeStart, rangeNum.value})
			if numUnendedRanges > 0 {
				rangeStart = rangeNum.value + 1
			} else {
				rangeStart = -1
			}
		}

	}
	// filter all that belong to splitSeedRanges
	for _, r := range splitRanges {
		if r[0] >= seedRangeStart && r[1] <= seedRangeEnd {
			splitSeedRanges = append(splitSeedRanges, r)
		}
	}
	return splitSeedRanges
}
