def main():
    f = open("input.txt", "r")
    tokens = 0
    while True:
        btn_a_vect = f.readline().split(":")[1].split(",")
        x_a = float(btn_a_vect[0].split("+")[1])
        y_a = float(btn_a_vect[1].split("+")[1])

        btn_b_vect = f.readline().split(":")[1].split(",")
        x_b = float(btn_b_vect[0].split("+")[1])
        y_b = float(btn_b_vect[1].split("+")[1])

        prize_vect = f.readline().split(":")[1].split(",")
        x_prize = float(prize_vect[0].split("=")[1]) + 10000000000000
        y_prize = float(prize_vect[1].split("=")[1]) + 10000000000000

        matrix = [
            [x_a, x_b, x_prize],
            [y_a, y_b, y_prize],
        ]


        pivot_element = matrix[0][0];
        matrix[0][0] /= pivot_element;
        matrix[0][1] /= pivot_element;
        matrix[0][2] /= pivot_element;

        first_row_multiple = matrix[1][0];
        matrix[1][0] -= first_row_multiple * matrix[0][0];
        matrix[1][1] -= first_row_multiple * matrix[0][1];
        matrix[1][2] -= first_row_multiple * matrix[0][2];

        num_b_presses = matrix[1][2] / matrix[1][1];
        num_a_presses = matrix[0][2] - (num_b_presses * matrix[0][1]);
        if(abs(num_a_presses - round(num_a_presses)) <= 0.01 and abs(num_b_presses - round(num_b_presses)) <= 0.01):
            tokens += 3 * round(num_a_presses) + 1 * round(num_b_presses)

        if(f.readline() != "\n"):
            break


    print(tokens)

if __name__ == "__main__":
    main()
