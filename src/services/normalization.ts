import { spawn } from 'child_process';

const PARVERSION_DIR = '/Users/david/projects/parversion/Cargo.toml';

export const normalizeWebAssetBundle = (bundle, url) => {
  const cargoRun = spawn('cargo', [
    'run',
    '--manifest-path',
    PARVERSION_DIR,
    '--',
    '-o',
    'json',
    '-u',
    url,
  ]);

  cargoRun.stdin.write(bundle.html);
  cargoRun.stdin.end();

  cargoRun.stdout.on('data', (data) => {
    console.log(`stdout: ${data}`);
  });

  cargoRun.stderr.on('data', (data) => {
    console.error(`stderr: ${data}`);
  });

  cargoRun.on('close', (code) => {
    console.log(`child process exited with code ${code}`);
  });

};
