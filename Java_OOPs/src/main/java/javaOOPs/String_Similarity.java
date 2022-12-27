package javaOOPs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import net.ricecode.similarity.JaroWinklerStrategy;
import net.ricecode.similarity.SimilarityStrategy;
import net.ricecode.similarity.StringSimilarityService;
import net.ricecode.similarity.StringSimilarityServiceImpl;

public class String_Similarity extends String_similarity_2

{

	

	public static void main(String[] args) 
	
	{
		
		
		SimilarityStrategy strategy = new JaroWinklerStrategy();
		String target = "subhash tomar ji";
		String source = "subhash tomar ji";
		StringSimilarityService service = new StringSimilarityServiceImpl(strategy);
		double score = service.score(source, target);
		System.out.println(score);
	

	}
}
