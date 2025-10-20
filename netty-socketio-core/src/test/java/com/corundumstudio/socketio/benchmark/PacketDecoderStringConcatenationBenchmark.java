/**
 * Copyright (c) 2012-2025 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.corundumstudio.socketio.benchmark;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * JMH Benchmark for PacketDecoder.addAttachment string concatenation optimization
 * 
 * This benchmark compares the performance of:
 * 1. Original string concatenation approach
 * 2. Optimized StringBuilder approach
 * 
 * The benchmark simulates the pattern building logic used in PacketDecoder.addAttachment
 * method for creating JSON placeholder patterns.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Fork(1)
public class PacketDecoderStringConcatenationBenchmark {

    @Param({"10", "50", "100", "500", "1000"})
    public int numberOfAttachments;

    /**
     * Original implementation using string concatenation
     * This simulates the old behavior in PacketDecoder.addAttachment
     */
    @Benchmark
    public String originalStringConcatenation() {
        String result = "";
        for (int i = 0; i < numberOfAttachments; i++) {
            // Simulate the original pattern building
            result = "{\"_placeholder\":true,\"num\":" + i + "}";
            if (i % 2 == 0) {
                result = "{\"num\":" + i + ",\"_placeholder\":true}";
            }
        }
        return result;
    }

    /**
     * Optimized implementation using StringBuilder
     * This simulates the optimized behavior in PacketDecoder.addAttachment
     */
    @Benchmark
    public String optimizedStringBuilder() {
        StringBuilder patternBuilder = new StringBuilder(32);
        String result = "";
        for (int i = 0; i < numberOfAttachments; i++) {
            // Simulate the optimized pattern building
            patternBuilder.setLength(0);
            patternBuilder.append("{\"_placeholder\":true,\"num\":").append(i).append("}");
            result = patternBuilder.toString();
            
            if (i % 2 == 0) {
                patternBuilder.setLength(0);
                patternBuilder.append("{\"num\":").append(i).append(",\"_placeholder\":true}");
                result = patternBuilder.toString();
            }
        }
        return result;
    }

    /**
     * Test with multiple pattern variations to simulate real-world usage
     * This benchmark tests the scenario where both patterns are tried
     */
    @Benchmark
    public String multiplePatternVariations() {
        StringBuilder patternBuilder = new StringBuilder(32);
        String result = "";
        for (int i = 0; i < numberOfAttachments; i++) {
            // Simulate multiple pattern variations (both patterns tried)
            patternBuilder.setLength(0);
            patternBuilder.append("{\"_placeholder\":true,\"num\":").append(i).append("}");
            result = patternBuilder.toString();
            
            // Try alternative pattern (simulating the fallback logic)
            patternBuilder.setLength(0);
            patternBuilder.append("{\"num\":").append(i).append(",\"_placeholder\":true}");
            result = patternBuilder.toString();
        }
        return result;
    }

    /**
     * Test with multiple pattern variations using string concatenation (original)
     */
    @Benchmark
    public String multiplePatternVariationsOriginal() {
        String result = "";
        for (int i = 0; i < numberOfAttachments; i++) {
            // Simulate multiple pattern variations with string concatenation
            result = "{\"_placeholder\":true,\"num\":" + i + "}";
            result = "{\"num\":" + i + ",\"_placeholder\":true}";
        }
        return result;
    }

    /**
     * Main method to run the benchmark
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(PacketDecoderStringConcatenationBenchmark.class.getSimpleName())
                .build();

        new Runner(opt).run();
    }
}
