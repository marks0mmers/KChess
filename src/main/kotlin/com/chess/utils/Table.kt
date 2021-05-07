package com.chess.utils

typealias Table<A, B, C> = Map<A, Map<B, C>>

operator fun <A, B, C> Table<A, B, C>.get(keys: Pair<A?, B?>) = get(keys.first)?.get(keys.second)