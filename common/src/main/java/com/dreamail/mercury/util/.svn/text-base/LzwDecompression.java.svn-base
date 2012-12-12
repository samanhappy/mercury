package com.dreamail.mercury.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class LzwDecompression {

	private final int BITS; // ֧�� 12 13 14 bits decompression
	private final int TABLE_SIZE;

	private final int MAX_VALUE;
	private final int MAX_CODE;

	private BufferedInputStream input = null;
	private BufferedOutputStream output = null;

	private int input_bit_count = 0;
	private int input_bit_buffer = 0;

	private short[] prefix_code;
	private short[] append_character;

	List<Short> string = new ArrayList<Short>();
	List<Short> decode_stack = new ArrayList<Short>();
	int decode_stack_count = 0;

	public LzwDecompression() {
		this(12);
	}

	public LzwDecompression(int bits) {
		BITS = bits;
		if (BITS <= 12) {
			TABLE_SIZE = 5021;
		} else if (BITS == 13) {
			TABLE_SIZE = 9029;
		} else
			TABLE_SIZE = 18041;
		prefix_code = new short[TABLE_SIZE];
		append_character = new short[TABLE_SIZE];
		MAX_VALUE = (1 << BITS) - 1;
		MAX_CODE = MAX_VALUE - 1;
	}

	public void expand(InputStream is, OutputStream os) throws IOException {

		input = new BufferedInputStream(is);
		output = new BufferedOutputStream(os);

		short next_code;
		short new_code;
		short old_code;
		short character;
		int counter;

		next_code = 256; /* This is the next available code to define */
		counter = 0; /* Counter is used as a pacifier. */
		System.out.println("Expanding...");

		old_code = input_code(input);
		character = old_code;
		output.write(old_code);
		while ((new_code = input_code(input)) != MAX_VALUE) {
			if (++counter == 1000) /* This section of code prints out */
			{ /* an asterisk every 1000 characters */
				counter = 0; /* It is just a pacifier. */
				System.out.printf("*");
			}
			string = new ArrayList<Short>();
			if (new_code >= next_code) {
				setListValue(decode_stack, decode_stack_count, character);
				string.add(character);
				decode_string(decode_stack_count + 1, old_code);

			} else {
				decode_string(decode_stack_count, new_code);
			}
			character = string.get(string.size() - 1);
			for (int i = string.size() - 1; i >= 0; i--) {
				output.write(string.get(i));
			}
			if (next_code <= MAX_CODE) {
				prefix_code[next_code] = old_code;
				append_character[next_code] = character;
				next_code++;
			}
			old_code = new_code;
		}
		input.close();
		output.close();
	}

	public String expand(InputStream is) throws IOException {
		input = new BufferedInputStream(is);
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BufferedOutputStream bufos = new BufferedOutputStream(output);
		short next_code;
		short new_code;
		short old_code;
		short character;
		int counter;

		next_code = 256; /* This is the next available code to define */
		counter = 0; /* Counter is used as a pacifier. */
		System.out.println("Expanding...");

		old_code = input_code(input);
		character = old_code;
		bufos.write(old_code);
		while ((new_code = input_code(input)) != MAX_VALUE) {
			if (++counter == 1000) /* This section of code prints out */
			{ /* an asterisk every 1000 characters */
				counter = 0; /* It is just a pacifier. */
				System.out.printf("*");
			}
			string = new ArrayList<Short>();
			if (new_code >= next_code) {
				setListValue(decode_stack, decode_stack_count, character);
				string.add(character);
				decode_string(decode_stack_count + 1, old_code);

			} else {
				decode_string(decode_stack_count, new_code);
			}
			character = string.get(string.size() - 1);
			for (int i = string.size() - 1; i >= 0; i--) {
				bufos.write(string.get(i));
			}
			if (next_code <= MAX_CODE) {
				prefix_code[next_code] = old_code;
				append_character[next_code] = character;
				next_code++;
			}
			old_code = new_code;
		}
		bufos.close();
		input.close();
		String content = output.toString("UTF-8");
		output.close();
		return content;
	}
	
	public String expand(byte[] b) throws IOException {
		input = new BufferedInputStream(new ByteArrayInputStream(b));
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BufferedOutputStream bufos = new BufferedOutputStream(output);
		short next_code;
		short new_code;
		short old_code;
		short character;
		int counter;

		next_code = 256; /* This is the next available code to define */
		counter = 0; /* Counter is used as a pacifier. */
		System.out.println("Expanding...");

		old_code = input_code(input);
		character = old_code;
		bufos.write(old_code);
		while ((new_code = input_code(input)) != MAX_VALUE) {
			if (++counter == 1000) /* This section of code prints out */
			{ /* an asterisk every 1000 characters */
				counter = 0; /* It is just a pacifier. */
				System.out.printf("*");
			}
			string = new ArrayList<Short>();
			if (new_code >= next_code) {
				setListValue(decode_stack, decode_stack_count, character);
				string.add(character);
				decode_string(decode_stack_count + 1, old_code);

			} else {
				decode_string(decode_stack_count, new_code);
			}
			character = string.get(string.size() - 1);
			for (int i = string.size() - 1; i >= 0; i--) {
				bufos.write(string.get(i));
			}
			if (next_code <= MAX_CODE) {
				prefix_code[next_code] = old_code;
				append_character[next_code] = character;
				next_code++;
			}
			old_code = new_code;
		}
		bufos.close();
		input.close();
		String content = output.toString("UTF-8");
		output.close();
		return content;
	}

	private static void setListValue(List<Short> list, int location, Short value) {
		if (location >= list.size()) {
			list.add(value);
		} else {
			list.set(location, value);
		}
	}

	private void decode_string(int decode_stack_address, short code) {
		int i;
		i = 0;
		decode_stack_count = decode_stack_address;
		while (code > 255) {
			setListValue(decode_stack, decode_stack_count,
					append_character[code]);
			decode_stack_count++;
			string.add(append_character[code]);
			code = prefix_code[code];
			if (i++ >= MAX_CODE) {
				System.out.println("Fatal error during code expansion.");
				System.exit(-3);
			}
		}
		setListValue(decode_stack, decode_stack_count, code);
		string.add(code);
	}

	private short input_code(BufferedInputStream bis) throws IOException {
		int return_value;

		while (input_bit_count <= 24) {
			input_bit_buffer |= bis.read() << (24 - input_bit_count);
			input_bit_count += 8;
		}
		return_value = input_bit_buffer >> (32 - BITS);

		/* C��Java��֮ͬ�� */
		if (return_value < 0)
			return_value += (MAX_VALUE + 1);
		input_bit_buffer <<= BITS;
		input_bit_count -= BITS;
		return (short) return_value;
	}

}
