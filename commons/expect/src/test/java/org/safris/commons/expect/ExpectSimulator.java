/*  Copyright 2010 Safris Technologies Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.safris.commons.expect;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpectSimulator {
    private static List<Prompt> prompts = new ArrayList<Prompt>();
    private static class Prompt {
        private List<String> answers = new ArrayList<String>();
        private String prompt;
        private String matchLeft;
        private Prompt left;
        private String matchRight;
        private Prompt right;

        public Prompt(String prompt, String matchLeft, Prompt left, String matchRight, Prompt right) {
            this.prompt = prompt;
            this.matchLeft = matchLeft;
            this.left = left;
            this.matchRight = matchRight;
            this.right = right;
            prompts.add(this);
        }

        public void setPrompt(String prompt) {
            this.prompt = prompt;
        }

        public String getPrompt() {
            return prompt;
        }

        public void setLeft(Prompt left) {
            this.left = left;
        }

        public Prompt getLeft() {
            return left;
        }

        public void setRight(Prompt right) {
            this.right = right;
        }

        public Prompt getRight() {
            return right;
        }

        public Prompt getNext(String line) {
            if (line.matches(matchLeft)) {
                answers.add(line);
                return left;
            }
            else if (line.matches(matchRight)) {
                answers.add(line);
                return right;
            }

            return this;
        }

        public void printAnswers() {
            for (String answer : answers)
                System.out.println(answer);
        }
    }

    private static final Prompt r2 = new Prompt("What is the name of the person?", ".*", null, ".*", null);
    private static final Prompt r14 = new Prompt("Quit?", "[yY]", null, "[nN]", null);
    private static final Prompt r13 = new Prompt("Print the roster?", "[yY]", r14, "[nN]", r2);
    private static final Prompt r12 = new Prompt("Will ${person} need a ride?", "[yY]", r13, "[nN]", r13);
    private static final Prompt r11 = new Prompt("Will ${person} neet to be driving?", "[yY]", r12, "[nN]", r13);
    private static final Prompt r10 = new Prompt("Will ${person} be drinking?", "[yY]", r11, "[nN]", r13);
    private static final Prompt r9 = new Prompt("Will ${person} bring stuff to the bbq?", "[yY]", r10, "[nN]", r10);
    private static final Prompt r8 = new Prompt("On what date will you see ${person}?", ".*", r9, ".*", r9);
    private static final Prompt r5 = new Prompt("Will the ${person} be attending the bbq?", "[yY]", r8, "[nN]", r2);
    private static final Prompt r7 = new Prompt("What is ${person}'s age?", ".*", r5, ".*", r5);
    private static final Prompt r6 = new Prompt("Is this a boy or a girl?", "[bB]", r7, "[gG]", r7);
    private static final Prompt r4 = new Prompt("Is the person a male or female?", "[mM]", r5, "[fF]", r5);
    private static final Prompt r3 = new Prompt("Is ${person} an adult or a child?", "[aA]", r4, "[cC]", r6);
    private static final Prompt r1 = new Prompt("Would you like to create a roster?", "[yY]", r2, "[nN]", r14);

    static
    {
        r2.setLeft(r3);
        r2.setRight(r3);
        r14.setRight(r1);
    }

    public static void main(String[] args) {
        if (args.length != 1)
            System.exit(1);

        System.out.println("Running for date: " + args[0]);
        Prompt prompt = r1;
        while (prompt != null) {
            System.out.print(prompt.getPrompt() + " ");
            final Scanner input = new Scanner(System.in);
            final String line = input.nextLine().trim();
            prompt = prompt.getNext(line);
        }

        for (Prompt p : prompts)
            p.printAnswers();

        System.out.println("Thanks for playing!");
    }
}
