package com.game.connect4;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
public class Game {

    
    private final @NonNull String player1;

    private final @NonNull String player2;

}