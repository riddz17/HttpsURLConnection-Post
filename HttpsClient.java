public class HttpsClient
{

	public static String post(String urlString, List<NameValuePair> params) {

		URL url;
		String response = null;
		try {
			url = new URL(urlString);

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);


			OutputStream os = conn.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					os, "UTF-8"));
			writer.write(getQuery(params));
			writer.flush();
			writer.close();
			os.close();
			conn.connect();

			InputStream is = null;
			try {
				is = conn.getInputStream();
				int ch;
				StringBuffer sb = new StringBuffer();
				while ((ch = is.read()) != -1) {
					sb.append((char) ch);
				}
				response = sb.toString();
			} catch (IOException e) {
				throw e;
			} finally {
				if (is != null) {
					is.close();
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	private static String getQuery(List<NameValuePair> params)
			throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : params) {
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		}

		return result.toString();
	}

}
