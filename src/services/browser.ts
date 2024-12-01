export const getWebAssetBundle = async (traditionalWindow, url: string): Promise<any> => {
  return new Promise((resolve, reject) => {
    traditionalWindow.loadURL(url);

    traditionalWindow.webContents.on('did-finish-load', async () => {
      try {
        const html = await traditionalWindow.webContents.executeJavaScript('document.documentElement.outerHTML');

        resolve({
          html
        });
      } catch(error) {
        console.error('Failed to retrieve HTML:', error);
        reject('Could not obtain web asset bundle');
      }
    });
  });
};
