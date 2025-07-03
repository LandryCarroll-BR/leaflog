import { useMutation, useQueryClient } from '@tanstack/react-query';
import { z } from 'zod';

import { api } from '@/lib/api-client';
import { MutationConfig } from '@/lib/react-query';
import { Plant } from '@/types/api';

import { getPlantsQueryOptions } from './get-plants';

export const addPlantBulkInputSchema = z.object({
  file: z.instanceof(FileList).transform((val) => {
    if (val.length === 0) {
      throw new Error('File is required');
    }
    return val[0];
  }),
});
export type AddPlantBulkInput = z.infer<typeof addPlantBulkInputSchema>;

export const addPlantBulk = async ({
  data,
}: {
  data: AddPlantBulkInput;
}): Promise<Plant[]> => {
  const formData = new FormData();
  formData.append('file', data.file);

  return await api.post('/plants/bulk', formData);
};

type UseAddPlantBulkOptions = {
  mutationConfig?: MutationConfig<typeof addPlantBulk>;
};

export const useAddPlantBulk = ({
  mutationConfig,
}: UseAddPlantBulkOptions = {}) => {
  const queryClient = useQueryClient();
  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    mutationFn: addPlantBulk,
    onSuccess: (...args) => {
      queryClient.invalidateQueries({
        queryKey: getPlantsQueryOptions().queryKey,
      });
      onSuccess?.(...args);
    },
    ...restConfig,
  });
};
