package net.erchen.adventofcode2021.day02;

import lombok.Getter;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Navigator {

    private AtomicInteger horizontal = new AtomicInteger(0);
    private AtomicInteger depth = new AtomicInteger(0);
    private AtomicInteger aim = new AtomicInteger(0);


    public void applyCommandsPart1(Collection<Command> commands) {
        commands.forEach(command -> {
            switch (command.getOperation()) {
                case up -> depth.addAndGet(-command.getArgument());
                case down -> depth.addAndGet(command.getArgument());
                case forward -> horizontal.addAndGet(command.getArgument());
            }
        });
    }

    public void applyCommandsPart2(Collection<Command> commands) {
        commands.forEach(command -> {
            switch (command.getOperation()) {
                case up -> aim.addAndGet(-command.getArgument());
                case down -> aim.addAndGet(command.getArgument());
                case forward -> {
                    horizontal.addAndGet(command.getArgument());
                    depth.addAndGet(aim.intValue() * command.getArgument());
                }
            }
        });
    }


}
