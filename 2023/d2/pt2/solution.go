package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

func main() {
	path, err := os.Getwd()
	if err != nil {
		log.Fatal(err)
	}
	file, err := os.Open(path + "/d2/input.txt")
	if err != nil {
		log.Fatal(err)
	}
	scanner := bufio.NewScanner(file)
	prodSum := 0
	for scanner.Scan() {
		line := scanner.Text()
		s := strings.Split(line, ":")[1]
		picks := strings.Split(s, ";")
		// minimum no. coloured cubes needed = maximum no. coloured cubes picked
		maxNumRed := 0
		maxNumGreen := 0
		maxNumBlue := 0
		for _, pick := range picks {
			colourCounts := strings.Split(pick, ", ")
			for _, colourCount := range colourCounts {
				colourCount = strings.TrimSpace(colourCount)
				s := strings.Split(colourCount, " ")
				count, err := strconv.Atoi(s[0])
				if err != nil {
					log.Fatal(err)
				}
				colour := s[1]
				if colour == "red" {
					if maxNumRed < count {
						maxNumRed = count
					}
				} else if colour == "blue" {
					if maxNumBlue < count {
						maxNumBlue = count
					}
				} else {
					if maxNumGreen < count {
						maxNumGreen = count
					}
				}
			}
		}
		prodSum += maxNumRed * maxNumBlue * maxNumGreen
	}
	fmt.Println(prodSum)
}
