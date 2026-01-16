//
// Created by Pavel Atanassov on 16.01.26.
//

#ifndef MATRIX_H
#define MATRIX_H

void identMatrix(float *matrix);
void saturateMatrix(float matrix[4][4], float* saturation);
void applyMatrix(Bitmap* bitmap, float matrix[4][4]);
void applyMatrixToPixel(unsigned char* red, unsigned char* green, unsigned char* blue, float matrix[4][4]);

#endif //MATRIX_H
