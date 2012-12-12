package com.dreamail.mercury.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LzwCompression {

	private final int BITS;
	private final int TABLE_SIZE;
	private final int HASHING_SHIFT = 4;
	private final int MAX_VALUE;
	private final int MAX_CODE;

	private final int EOF = -1;

	private BufferedInputStream input = null;
	private BufferedOutputStream output = null;

	private int output_bit_count = 0;
	private int output_bit_buffer = 0;

	private short[] code_value;
	private short[] prefix_code;
	private short[] append_character;

	public LzwCompression() {
		this(12);
	}

	public LzwCompression(int bits) {
		BITS = bits;
		if (BITS <= 12) {
			TABLE_SIZE = 5021;
		} else if (BITS == 13) {
			TABLE_SIZE = 9029;
		} else
			TABLE_SIZE = 18041;
		code_value = new short[TABLE_SIZE];
		prefix_code = new short[TABLE_SIZE];
		append_character = new short[TABLE_SIZE];

		MAX_VALUE = (1 << BITS) - 1;
		MAX_CODE = MAX_VALUE - 1;
	}

	public void compress(InputStream is, OutputStream os) throws IOException {
		input = new BufferedInputStream(is);
		output = new BufferedOutputStream(os);
		short next_code = 0;
		short character = 0;
		short string_code = 0;
		short index = 0;

		next_code = 256;

		for (short i = 0; i < TABLE_SIZE; i++)
			code_value[i] = -1;

		string_code = (short) input.read();

		while ((character = (short) input.read()) != EOF) {
			index = find_match(string_code, character);

			if (code_value[index] != -1) {
				string_code = code_value[index];
			} else {
				if (next_code <= MAX_CODE) {
					code_value[index] = next_code++;
					prefix_code[index] = string_code;
					append_character[index] = character;
				}

				output_code(string_code);
				string_code = character;
			}
		}

		output_code(string_code);
		output_code((short) MAX_VALUE);
		output_code((short) 0);

		output.close();
		input.close();

	}

	public void compress(String str, OutputStream os) throws IOException {
		InputStream is = new ByteArrayInputStream(str.getBytes());
		input = new BufferedInputStream(is);
		output = new BufferedOutputStream(os);
		short next_code = 0;
		short character = 0;
		short string_code = 0;
		short index = 0;
		next_code = 256;

		for (short i = 0; i < TABLE_SIZE; i++)
			code_value[i] = -1;

		string_code = (short) input.read();

		while ((character = (short) input.read()) != EOF) {
			index = find_match(string_code, character);

			if (code_value[index] != -1) {
				string_code = code_value[index];
			} else {
				if (next_code <= MAX_CODE) {
					code_value[index] = next_code++;
					prefix_code[index] = string_code;
					append_character[index] = character;
				}

				output_code(string_code);
				string_code = character;
			}
		}

		output_code(string_code);
		output_code((short) MAX_VALUE);
		output_code((short) 0);
		output.close();
		input.close();
	}
	
	public byte[] compress(String str) throws IOException {
		InputStream is = new ByteArrayInputStream(str.getBytes("UTF-8"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		output = new BufferedOutputStream(baos);
		input = new BufferedInputStream(is);
		short next_code = 0;
		short character = 0;
		short string_code = 0;
		short index = 0;
		next_code = 256;

		for (short i = 0; i < TABLE_SIZE; i++)
			code_value[i] = -1;

		string_code = (short) input.read();

		while ((character = (short) input.read()) != EOF) {
			index = find_match(string_code, character);

			if (code_value[index] != -1) {
				string_code = code_value[index];
			} else {
				if (next_code <= MAX_CODE) {
					code_value[index] = next_code++;
					prefix_code[index] = string_code;
					append_character[index] = character;
				}

				output_code(string_code);
				string_code = character;
			}
		}

		output_code(string_code);
		output_code((short) MAX_VALUE);
		output_code((short) 0);
		output.close();
		input.close();
		byte[] content = baos.toByteArray();
		System.out.println(new String(content));
		baos.close();
		return content;
	}

	private short find_match(short hash_prefix, short hash_character) {
		int index = 0;
		int offset = 0;

		index = (hash_character << HASHING_SHIFT) ^ hash_prefix;

		if (index == 0)
			offset = 1;
		else
			offset = TABLE_SIZE - index;

		while (true) {
			if (code_value[index] == -1)
				return (short) index;
			if (prefix_code[index] == hash_prefix
					&& append_character[index] == hash_character)
				return (short) index;
			index -= offset;
			if (index < 0)
				index += TABLE_SIZE;
		}
	}

	private void output_code(short code) throws IOException {
		output_bit_buffer |= code << (32 - BITS - output_bit_count);
		output_bit_count += BITS;
		while (output_bit_count >= 8) {
			output.write(output_bit_buffer >> 24);
			output_bit_buffer <<= 8;
			output_bit_count -= 8;
		}
	}
}
