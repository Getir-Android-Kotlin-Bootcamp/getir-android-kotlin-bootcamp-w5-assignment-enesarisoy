package com.ns.enesarisoyweek5

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //PAGE 6
        // Creating a CoroutineScope running on a background thread.
        // After a delay of 1000 milliseconds, prints "First page. 1000 ms delay".
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            println("First page. 1000 ms delay")
        }

        //PAGE 7
        // Initiating an asynchronous operation that returns "Merhaba!".
        val deferred = GlobalScope.async {
            "Merhaba!"
        }
        // Using runBlocking to wait for the result of the asynchronous operation and print it.
        runBlocking {
            val mesaj = deferred.await()
            println(mesaj)
        }

        //PAGE 8
        // Creating a CoroutineScope with runBlocking and waiting until the enclosed operation completes.
        // After a delay of 5000 milliseconds, prints "Coroutine tamamlandı".
        runBlocking {
            delay(5000)
            println("Coroutine tamamlandı")
        }

        //PAGE 9
        // Launching a coroutine in the Main dispatcher to update the UI.
        // Printing the message "UI güncellendi!".
        CoroutineScope(Dispatchers.Main).launch {
            val text = "UI güncellendi!"
            println(text)
        }

        //PAGE 10
        // Launching a coroutine in the IO dispatcher to perform a network request.
        // Reading text from a URL and printing the response.
        CoroutineScope(Dispatchers.IO).launch {
            val url = "https://example.com/api/data"
            val response = URL(url).readText()
            println(response)
        }

        //PAGE 11
        // Creating a CoroutineScope running on a background thread.
        // Calculating the sum of numbers from 0 to 10000 and printing the result.
        CoroutineScope(Dispatchers.Default).launch {
            var sum = 0
            for (i in 0..10000) {
                sum += i
            }
            println("Toplam: $sum")
        }


        //PAGE 12
        // Performing a network request in a coroutine with Dispatchers.IO and printing the response.
        val url = "https://api.example.com/data"

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                val response = URL(url).readText()
                println("Veri: $response")
            }
        }

        val scope = CoroutineScope(Dispatchers.IO)

        fun myCoroutine(job: Job) {
            scope.launch {
                withContext(job) {

                }
            }
        }


        //PAGE 13
        // Creating a CoroutineScope with Dispatchers.IO.
        // Launching a coroutine with a Job and immediately cancelling it.
        val scope13 = CoroutineScope(Dispatchers.IO)
        val job = Job()

        scope13.launch(job) {
        }
        job.cancel()


        //PAGE 15
        // Simulate fetching numbers with a delay.
        fun getNumbers(delay: Long): List<Int> {
            val numbers = mutableListOf<Int>()
            for (i in 1..10) {
                Thread.sleep(delay)
                numbers.add(i)
            }
            return numbers
        }

        fun main() {
            val numbers = getNumbers(100)
            for (number in numbers) {
                println(number)
            }
        }


        //PAGE 16
        // Defining a function that emits numbers with a delay as a Flow.
        fun getNumbersFlow(delay: Long): Flow<Int> {
            return flow {
                for (i in 1..10) {
                    emit(i)
                    delay(delay)
                }
            }
        }

        // Collecting and printing the numbers emitted by the flow.
        runBlocking {
            val flow = getNumbersFlow(100)
            launch {
                flow.collect { number ->
                    println(number)
                }
            }.join()
        }


        //PAGE 17
        // Defining a CoroutineScope.
        val scope17 = CoroutineScope(Dispatchers.IO)

        scope17.launch {
        }


        //PAGE 18
        // Creating a SupervisorJob and a CoroutineScope with it.
        val supervisorJob = SupervisorJob()
        val scope18 = CoroutineScope(supervisorJob)

        scope18.launch {
        }

        scope18.launch {
        }


        //PAGE 19
        // Creating a UnSupervisorJob and a CoroutineScope with it.
        val unsupervisorJob = SupervisorJob()
        val scope19 = CoroutineScope(unsupervisorJob)

        scope19.launch {
        }

        scope19.launch {
        }


        //PAGE 20
        // Creating a CoroutineScope with the Main dispatcher.
//        val uiScope = MainCoroutineScope()
        val uiScope = CoroutineScope(Dispatchers.Main)

        uiScope.launch {
        }


        //PAGE 21
//        val supervisorScope = SupervisorScope()
        val supervisorScope = CoroutineScope(Job())

        supervisorScope.launch {
        }


        //PAGE 23
        // Defining a suspend lambda that creates a flow emitting three integers.
        suspend {
            val flow = flow {
                emit(1)
                emit(2)
                emit(3)
            }
            flow.collect { deger -> println(deger) }
        }

        //PAGE 24
        // Defining a suspend lambda that creates a flow from a list of integers.
        suspend {
            val sayilar = listOf(1, 2, 3)
            val flow = sayilar.asFlow()

            flow.collect { deger -> println(deger) }
        }


        //PAGE 25
        // Defining a suspend lambda that creates a flow emitting three integers.
        // Mapping the emitted values to their squares and printing the results.
        suspend {

            val sayilarFlow = flow {
                emit(1)
                emit(2)
                emit(3)
            }
            val karelerFlow = sayilarFlow.map { sayi -> sayi * sayi }
            karelerFlow.collect { kare -> println(kare) }
        }


        //PAGE 26
        // Defining a suspend lambda that creates a flow emitting five integers.
        // Filtering the emitted values to select only odd numbers and printing them.
        suspend {

            val sayilarFlow = flow {
                emit(1)
                emit(2)
                emit(3)
                emit(4)
                emit(5)
            }
            val teklerFlow = sayilarFlow.filter { sayi -> sayi % 2 == 1 }
            teklerFlow.collect { tek -> println(tek) }
        }


        //PAGE 27
        // Defining a suspend function to simulate a delayed request.
        // Using a flow to emit requests, transforming each request into a message and then a response.
        suspend fun performRequest(request: Int): String {
            delay(1000)
            return "response $request"
        }
        runBlocking<Unit> {
            (1..3).asFlow()
                .transform { request ->
                    emit("Making request $request")
                    emit(performRequest(request))
                }
                // Collecting and printing the transformed responses.
                .collect { response -> println(response) }
        }


        //PAGE 28
        // Defining a suspend lambda that creates a flow emitting five integers.
        suspend {

            val sayilarFlow = flow {
                emit(1)
                emit(2)
                emit(3)
                emit(4)
                emit(5)
            }
            // Collecting and printing the values emitted by the flow, switching to the IO dispatcher for collection.
            sayilarFlow
                .flowOn(Dispatchers.IO)
                .collect { sayi -> println(sayi) }
        }


        //PAGE 31
        // Creating a Channel to communicate between coroutines.
        // Launching a coroutine as a sender and another as a receiver, exchanging a single value.
        runBlocking {
            val kanal = Channel<Int>()

            launch {
                println("Gönderici başlatıldı")
                kanal.send(10)
                println("Değer gönderildi: 10")
            }

            launch {
                println("Alıcı başlatıldı")
                val deger = kanal.receive()
                println("Değer alındı: $deger")
            }
        }


        //PAGE 32
        // Creating a Channel to communicate between coroutines.
        // Launching a coroutine as a sender, continuously sending messages, and another as a receiver,
        // continuously receiving and printing messages.
        runBlocking {
            val kanal = Channel<String>()

            launch {
                println("Gönderici başlatıldı")
                for (i in 1..5) {
                    kanal.send("Mesaj $i")
                    println("Mesaj gönderildi: Mesaj $i")
                }
            }

            launch {
                println("Alıcı başlatıldı")
                while (true) {
                    val mesaj = kanal.receive()
                    println("Mesaj alındı: $mesaj")
                }
            }
        }


        //PAGE 33
        // Creating a ticker Channel to emit periodic events.
        // Launching a coroutine as a receiver for the ticker events and printing a message for each tick.
        // Delaying the main coroutine for 5000 milliseconds to observe ticker events.
        runBlocking {
            val kanal = ticker(1000, 0)
            launch {
                println("Alıcı başlatıldı")
                while (true) {
                    kanal.receive()
                    println("Tıklama alındı")
                }
            }
            delay(5000)
        }
    }
}