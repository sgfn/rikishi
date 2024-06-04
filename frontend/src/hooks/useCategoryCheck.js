import { useEffect, useState } from 'react';

// hook do sprawdzania prawidłowości kategorii wagowej zawodnika
const useCategoryCheck = (url) => {
  const [wrongCategoryFlag, setWrongCategoryFlag] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const abort = new AbortController();
    fetch(url, { signal: abort.signal })
      .then((res) => {
        if (!res.ok) {
          throw Error('Could not check weight category.');
        }
        return res.json();
      })
      // eslint-disable-next-line @typescript-eslint/no-shadow,promise/always-return
      .then((data) => {
        setWrongCategoryFlag(!data.hasValidCategory);
        setError(null);
      })
      .catch((err) => {
        if (err.name === 'AbortError') {
          // eslint-disable-next-line no-console
          console.log('Fetch aborted');
        } else {
          setError(err.message);
        }
      });
    return () => abort.abort();
  }, [url]);
  return { wrongCategoryFlag, error };
};

export default useCategoryCheck;
