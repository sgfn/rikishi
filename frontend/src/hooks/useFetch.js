import { useEffect, useState } from 'react';

// hook do wczytywania danych
const useFetch = (url) => {
  const [data, setData] = useState(null);
  const [isPending, setIsPending] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const abort = new AbortController();
    fetch(url, { signal: abort.signal })
      .then((res) => {
        if (!res.ok) {
          throw Error('Could not fetch data.');
        }
        return res.json();
      })
      // eslint-disable-next-line @typescript-eslint/no-shadow,promise/always-return
      .then((data) => {
        setData(data);
        setIsPending(false);
        setError(null);
      })
      .catch((err) => {
        if (err.name === 'AbortError') {
          // eslint-disable-next-line no-console
          console.log('Fetch aborted');
        } else {
          setError(err.message);
          setIsPending(false);
        }
      });
    return () => abort.abort();
  }, [url]);
  return { data, isPending, error };
};

export default useFetch;
