package com.pardeep.yogify.admin.fragments

import android.app.Application
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.SupabaseClientBuilder
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
/*
bucket:yogify_storage
Url: https://lecfqdaoullqqrkximvq.supabase.co
key: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImxlY2ZxZGFvdWxscXFya3hpbXZxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzI2ODY4MDQsImV4cCI6MjA0ODI2MjgwNH0.Myd_8-Exs9LkCpNITCbL5mnz2548nsJkFooslTkmW2Y
*/

class MyApplication:Application() {
    lateinit var supabaseClient: SupabaseClient
    override fun onCreate() {
        super.onCreate()
        supabaseClient = createSupabaseClient(
            "https://lecfqdaoullqqrkximvq.supabase.co",  // Your Supabase URL
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImxlY2ZxZGFvdWxscXFya3hpbXZxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MzI2ODY4MDQsImV4cCI6MjA0ODI2MjgwNH0.Myd_8-Exs9LkCpNITCbL5mnz2548nsJkFooslTkmW2Y"
        ) {
            install(Storage)
            // Additional configuration can be added here if needed
        }

        // Now you can access Supabase Auth and Storage through the `supabaseClient`
//        val auth = supabaseClient.auth
        val bucket = supabaseClient.storage.from("yogify_storage")

    }
}