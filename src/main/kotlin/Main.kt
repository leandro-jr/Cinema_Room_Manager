// cinema prices
const val PRICEFIRST = 10
const val PRICESECOND = 8

fun printCinema(seats: Int, rows: Int, cinema: MutableList<MutableList<String>>): Unit {
    // print representation of cinema seats
    println("Cinema:")
    print(" ")
    for (i in 1..seats) {
        print(" $i")
    }
    print("\n")
    for (i in 0..rows - 1) {
        println("${i + 1}" + " ${cinema[i].joinToString(" ")}")
    }
}

fun buyTicket(seats: Int, rows: Int, firstHalf: Int, cinema: MutableList<MutableList<String>>): MutableList<MutableList<String>> {
    // ask user to choose seat, determine ticket price and print it to user
    var placeTaken = false
    var bookedRow = 0
    var bookedSeat = 0
    do {
        try {
            println()
            println("Enter a row number:")
            bookedRow = readLine()!!.toInt()
            println("Enter a seat number in that row:")
            bookedSeat = readLine()!!.toInt()

            // test to catch IndexOutOfBoundsException
            cinema[bookedRow - 1][bookedSeat - 1]

            // verify if seat was previously purchased
            placeTaken = false
            for (index in cinema.indices) {
                if (index == bookedRow - 1) {
                    for (seatIndex in cinema[index].indices) {
                        if (seatIndex == bookedSeat - 1 && cinema[index][seatIndex] == "B") {
                            throw Exception("That ticket has already been purchased!")
                        }
                    }
                }
            }
        } catch (e: IndexOutOfBoundsException) {
            println("Wrong input!")
            placeTaken = true
        } catch (e: Exception) {
            println(e.message)
            placeTaken = true
        }
    } while (placeTaken == true)

    // determine ticket price based on seat place
    if (rows * seats <= 60) {
        println("Ticket price: $$PRICEFIRST")
    } else {
        if (bookedRow <= firstHalf) {
            println("Ticket price: $$PRICEFIRST")
        } else {
            println("Ticket price: $$PRICESECOND")
        }
    }

    // book the seat
    cinema[bookedRow - 1][bookedSeat - 1] = "B"
    return cinema
}

fun statistics(seats: Int, rows: Int, firstHalf: Int, secondHalf: Int, cinema: MutableList<MutableList<String>>): Unit {
    // provide cinema statistics
    var currentIncome = 0
    var numberOfPurchasedTickets = 0

    // calculate number of purchased tickets and current income
    for (index in cinema.indices) {
        for (seat in cinema[index]) {
            if (seat == "B") {
                numberOfPurchasedTickets += 1
                if (rows * seats <= 60) {
                    currentIncome += PRICEFIRST
                } else {
                    if (index + 1 <= firstHalf) {
                        currentIncome += PRICEFIRST
                    } else {
                        currentIncome += PRICESECOND
                    }
                }
            }
        }
    }

    // calculate percentage of tickets booked with 2 digits
    val percentage: Double = 100 * (numberOfPurchasedTickets.toDouble() / (rows * seats))
    val percentage2digits = String.format("%.2f", percentage)

    // calculate potential total income if cinema is completely booked
    var totalIncome = 0
    if (rows * seats <= 60) {
        totalIncome = rows * seats * PRICEFIRST
    } else {
        totalIncome = firstHalf * seats * PRICEFIRST + secondHalf * seats * PRICESECOND
    }

    // present statistics to user
    println()
    println("Number of purchased tickets: $numberOfPurchasedTickets")
    println("Percentage: $percentage2digits%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}

fun main() {
    // First the user will define the user size. After that, user can choose to display cinema layout, buy tickets,
    // show cinema statistics or exit the program.

    // define cinema size
    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val seats = readLine()!!.toInt()
    // create cinema
    var cinema = MutableList(rows) { MutableList(seats) {"S"} }
    // define first and second half of the cinema. It will be used to calculate ticket price
    val firstHalf = rows / 2
    val secondHalf = rows - firstHalf

    // presents menu to user
    do {
        println()
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        val option = readLine()!!.toInt()
        when (option) {
            1 -> printCinema(seats, rows, cinema)
            2 -> cinema = buyTicket(seats, rows, firstHalf, cinema)
            3 -> statistics(seats, rows, firstHalf, secondHalf, cinema)
            0 -> continue
        }
    } while (option != 0)
}

