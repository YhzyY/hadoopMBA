package hw4MBA;
 
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapred.join.TupleWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;
import hw4MBA.SortMBA;
 
public class MapReduceMBA {
 
	public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
 
		private Text data = new Text();
 
		public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			
			String[] products = value.toString().split(",");
			String[] val = products[1].split(" ");
			List<String> list = new ArrayList<String>(Arrays.asList(val));
			list.removeAll(Arrays.asList("", null));
			List<List<String>> result = SortMBA.findsort(list, 2);
			
//			String[] products = value.toString().split(",");
//			String[] val = products[1].split(" ");
//			List<String> list = convertToList(val);
//			List<List<String>> result = SortMBA.findsort(list, 2);
			
			
//			String[] val = value.toString().split(" ");
//			List<String> list = convertToList(val);
//			List<List<String>> result = SortMBA.findsort(list, 2);
 
 
			for (List<String> tuple : result) {
 
				context.write(new Text(tuple.toString()), new IntWritable(1));
			}
 
		}
 
	}
 
	public static List<String> convertToList(String[] val) {
 
		List<String> list = new ArrayList<String>();
 
		for (int i = 0; i < val.length; i++) {
			list.add(val[i]);
		}
 
		return list;
 
	}
 
	public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
 
		public void reduce(Text key, Iterable<IntWritable> value, Context context)
				throws IOException, InterruptedException {
 
			int sum = 0;
			for (IntWritable values : value) {
 
				sum += 1;
 
			}
 
			context.write(new Text(key), new IntWritable(sum));
 
		}
	}
 
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		
		final Log LOG = LogFactory.getLog(MapReduceMBA.class);
 
	    Configuration conf = new Configuration();
	    GenericOptionsParser optionParser = new GenericOptionsParser(conf, args);
	    String[] remainingArgs = optionParser.getRemainingArgs();
	    if ((remainingArgs.length != 2)) {
	      System.err.println("Usage: MBA <input> <output>");
	      System.exit(2);
	    }
	    List<String> otherArgs = new ArrayList<String>();
	    for (int i=0; i < remainingArgs.length; ++i) {
	      otherArgs.add(remainingArgs[i]);
	    }
	    Job job = Job.getInstance(conf, "MarketBasketAnalysis"); 
		job.setJarByClass(MapReduceMBA.class);
		job.setMapperClass(TokenizerMapper.class);
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		job.setReducerClass(IntSumReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setNumReduceTasks(1);
	    FileInputFormat.addInputPath(job, new Path(otherArgs.get(0)));
	    FileOutputFormat.setOutputPath(job, new Path(otherArgs.get(1)));
		System.exit(job.waitForCompletion(true) ? 0 : 1);

	    
		 //genericOptionalParser
//		@SuppressWarnings("deprecation")
//		Job job = new Job();

		
//		Configuration conf = new Configuration();
//		Job job = Job.getInstance(conf, "MarketBasketAnalysis");  //genericOptionalParser
////		@SuppressWarnings("deprecation")
////		Job job = new Job();
//		job.setJarByClass(MapReduceMBA.class);
//		job.setMapperClass(TokenizerMapper.class);
//		job.setMapOutputKeyClass(Text.class);
//		job.setMapOutputValueClass(IntWritable.class);
//		job.setReducerClass(IntSumReducer.class);
//		job.setOutputKeyClass(Text.class);
//		job.setOutputValueClass(IntWritable.class);
//		job.setNumReduceTasks(1);
//		FileInputFormat.addInputPath(job, new Path(args[0]));
//		FileOutputFormat.setOutputPath(job, new Path(args[1]));
//		System.exit(job.waitForCompletion(true) ? 0 : 1);

 
	}
}