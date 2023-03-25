package com.nima.guessthatpokemon.client

import com.apollographql.apollo3.ApolloClient

val apolloClient = ApolloClient.Builder()
    .serverUrl("https://beta.pokeapi.co/graphql/v1beta")
    .build()